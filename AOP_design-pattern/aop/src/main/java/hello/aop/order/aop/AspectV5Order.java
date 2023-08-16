package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * 아래처럼 Order은 Aspect단위지 어드바이스 단위가 아님
 * 따라서 Aspect를 모아놓은 클래스에 내부 클래스를 새로 만들어서 @Aspect와 @Order을 적용해 주거나 개별 클래스를 만들어 주면 됨
 */
@Slf4j
@Aspect
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect{
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws  Throwable{
            log.info("[log] {}",joinPoint.getSignature()); //[log] void hello.aop.order.OrderService.orderItem(String)
            return joinPoint.proceed();
        }

    }

    @Aspect
    @Order(1)
    public static class TransactionAspect{
        @Around("hello.aop.order.aop.Pointcuts.allOrderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
            try {
                log.info("[트랜잭션 시작]",joinPoint.getSignature());
                Object result = joinPoint.proceed(); //타겟 포인트 호출
                log.info("[트랜잭션 커밋]",joinPoint.getSignature());
                return result;
            }catch (Exception e){
                log.info("[트랜잭션 롤백]",joinPoint.getSignature());
                throw e;
            }finally {
                log.info("[리소스 릴리즈]",joinPoint.getSignature());
            }

        }
    }




}
