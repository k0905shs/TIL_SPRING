package hello.async.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * ExceptionHandler 란 단순하게 말해서 예외상황 발생 시 한 곳에서 처리 하겠다는 의미
 * 비동기 로직을 처리하다 보면 해당 로직이 실패했는지 성공했는지 관리해야할 필요가 있다.
 */
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Async Thread Task Error");
        log.error("Exception Message : {}", ex.getMessage());
        log.error("Method Name : {}", method.getName());
        for (Object param : params) {
            log.error("param : {}", param);
        }
        /**
         * 추가 예외처리 로직
         */
    }
}
