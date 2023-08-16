package hello.toy.logtrace;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Aspect
@Component
public class LogTraceAspect {

    private static final String START = " --> ";
    private static final String COMPLETE = " <-- ";
    private static final String EXCEPT = " x-- ";
    private static final String BLANK = "|   |";
    private ThreadLocal<Trace> traceLocal = new ThreadLocal<>();

    @Pointcut("execution(* hello.toy..*(..))")
    private void anyPublicOperation() {} // pointcut annotation 값을 참조하기 위한 dummy method

//    @Around("@annotation(logTrace)") //logTrace 어노테이션이 붙은 경우
    @Around("anyPublicOperation()")
//    public Object doTrace(ProceedingJoinPoint joinPoint, LogTrace logTrace) throws Throwable {//logTrace 어노테이션이 붙은 경우
    public Object doTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        String location = joinPoint.getSignature().toString();  //AOP 실행 위치 정보
        Exception exceptionHolder = null; //예외 발생시 예외를 로직 실행 이 후에 throw 해주기 위한 변수

        //init Controller에서 처음 request가 들어와서 실행된 경우 traceLocal 초기화 UUID는 나중에 IP든  Session 정보든 사용하면 될듯?
        if(traceLocal.get() == null)
            traceLocal.set(new Trace(UUID.randomUUID().toString().substring(0,8),1));

//        1. --> 출력 2.프록시 내부 원래 로직 실행 3. <-- 출력
        try {
            go(location,traceLocal.get(),exceptionHolder); //호출 로그 (1 출력)
            traceLocal.get().nextLevel(); // 다음 레벨 로그 출력을 위한 위한 레벨 업 (2) 프록시 내부 원래 로직 실행
            return joinPoint.proceed(); //프록시 내부 로직 실행
        } catch (Exception e) {
            exceptionHolder = e;//예외 hold
        }finally {//항상 실행되야 하는 로직 (3 출력),  
            traceLocal.get().prevLevel();//레벨 증가 하기 전와 같은 레벨로 돌리기 위해 레벨 마이너스
            back(location,traceLocal.get(),exceptionHolder); // 반환 출력
            if(traceLocal.get().isLevelOne())//만약 레벨이 1이면 쓰레드 로컬 변수 제거
                traceLocal.remove();
        }
        throw exceptionHolder; //예외 throw
    }

    private void go(String location,Trace trace,Exception e){
        int level = trace.getLevel();
        String blank = makeBlank(level);
            log.info("[{}]  {}{}, location : {}, level : {}",trace.getTraceId(),blank,START,location,level);

    }

    private void back(String location,Trace trace,Exception e){
        int level = trace.getLevel();
        String blank = makeBlank(level);
        if(e == null)
            log.info("[{}]  {}{}, location : {}, level : {}",trace.getTraceId(),blank,COMPLETE,location,level);
        else
            log.info("[{}]  {}{}, location : {}, level : {}, Exception : {}",trace.getTraceId(),blank,EXCEPT,location,level,e.toString());
    }

    private String makeBlank(int level){
        StringBuilder result = new StringBuilder();
        for (int i=1 ; i < level; i++){
            result.append(BLANK);
        }
        return result.toString();
    }


}
