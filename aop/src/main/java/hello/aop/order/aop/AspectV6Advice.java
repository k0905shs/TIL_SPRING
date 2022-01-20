package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 아래처럼 Order은 Aspect단위지 어드바이스 단위가 아님
 * 따라서 Aspect를 모아놓은 클래스에 내부 클래스를 새로 만들어서 @Aspect와 @Order을 적용해 주거나 개별 클래스를 만들어 주면 됨
 */
@Slf4j
@Aspect
public class AspectV6Advice {

    /**
     * 어드바이스는 Around 외에도 여러 가지가 있다.
     * Around : 메소드 호출 전후에 수행, 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환등
     * Before : 조인 포인트 실행 이전에 실행
     * @After Returning : 조인 포인트가 정상 완료후 실행
     * After Throwing : 메서드가 예외를 던지는 경우 실행
     * After : 조인 포인트가 정상 또는 예외에 관계없이 실행 (finally)
     * 
     * ProceedingJoinPoint 이거는 Around 에만 사용할 수 잇음
     */

//    @Around("hello.aop.order.aop.Pointcuts.allOrderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable
//    {
//        try {
//            //@Before
//            log.info("[around][트랜잭션 시작] {}", joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//            //@AfterReturning
//            log.info("[around][트랜잭션 커밋] {}", joinPoint.getSignature());
//            return result;
//        } catch (Exception e) {
//            //@AfterThrowing
//            log.info("[around][트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        } finally {
//            //@After
//            log.info("[around][리소스 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    @Before("hello.aop.order.aop.Pointcuts.allOrderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrderAndService()",
            returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.allOrderAndService()",
            throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(),
                ex.getMessage());
    }

    @After(value = "hello.aop.order.aop.Pointcuts.allOrderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }




}
