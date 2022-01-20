package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    /**
     * 어라운드 어노테이션의 값인 execution(* hello.aop.order..*(..))는 포인트컷
     * 이 포인트컷은 AspectJ의 표현식이다.
     * 어라운드 어노테이션의 메소드인 doLog는 어드바이스
     *
     * 이제 OrderService OrderRepository의 모든 메소드는 AOP의 대상이 된다.
     * *스프링은 프록시 방식의 AOP를 사용하므로 프록시를 통하는 메서드만 적용 대상이 된다. *
     *
     * 로그 정보
     * 2022-01-20 10:25:14.049  INFO 2380 --- [           main] hello.aop.order.aop.AspectV1             : [log] void hello.aop.order.OrderService.orderItem(String)
     * 2022-01-20 10:25:14.049  INFO 2380 --- [           main] hello.aop.order.OrderService             : [orderService] 실행
     * 2022-01-20 10:25:14.049  INFO 2380 --- [           main] hello.aop.order.aop.AspectV1             : [log] String hello.aop.order.OrderRepository.save(String)
     * 2022-01-20 10:25:14.049  INFO 2380 --- [           main] hello.aop.order.OrderRepository          : [orderRepository] 실행
     */
    @Around("execution(* hello.aop.order..*(..))") 
    public Object doLog(ProceedingJoinPoint joinPoint) throws  Throwable{
        log.info("[log] {}",joinPoint.getSignature()); //[log] void hello.aop.order.OrderService.orderItem(String)
        return joinPoint.proceed();
    }

}
