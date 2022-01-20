package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    /**
     * 포인트컷 표현식 분리해서 적용
     * 메서드 이름과 포인트컷 파라미터를 합쳐서 '포인트컷 시그니처'라고 한다.
     *
     *  접근 제어자는 public private둘다 사용 가능하고 외부에서 사용한다면 public
     *
    */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //포인트컷 시그니처


    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws  Throwable{
        log.info("[log] {}",joinPoint.getSignature()); //[log] void hello.aop.order.OrderService.orderItem(String)
        return joinPoint.proceed();
    }

}
