package hello.async.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Slf4j
@Service("testService")
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{

    @Resource(name = "v1_executor")
//    private Executor executor1; //JAVA concurrent Executor
    private ThreadPoolTaskExecutor executor1; //ThreadPoolTaskExecutor 추가 메소드 사용 가능

    @Async //선언한 빈을 주입 받아서 사용하는 방법
    @Override
    public void asyncThreadTestV1() {
        Runnable runnable = () -> { // Runnable 매게변수 구현
            try {
                log.info("Thread Task V1 Run");
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1500);
                    log.info("{} - index {}", executor1.getThreadNamePrefix(), i);
                }
                log.info("Thread Task V1 End");
            } catch (Exception e) {
                log.error("Thread Task V1 Exception : {}", e.toString());
            }
        };
        executor1.execute(runnable);
    }

    @Async("v2_executor") //어노테이션에 빈 사용할 빈 네임 사용
    @Override
    public void asyncThreadTestV2() {
        try {
            log.info("Thread Task V2 Run");
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                log.info("{} - index {}", Thread.currentThread().getName(), i);
            }
            log.info("Thread Task V2 End");
        } catch (Exception e) {
            log.error("Thread Task V2 Exception : {}", e.toString());
        }
    }

    @Async //AsyncConfigurer인터페이스의 getAsyncExecutor를 구현하여 사용하는 방식
    @Override
    public void asyncThreadTestV3() {
        try {
            log.info("Thread Task V3 Run");
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                log.info("{} - index {}", Thread.currentThread().getName(), i);
            }
            log.info("Thread Task V3 End");
        } catch (Exception e) {
            log.error("Thread Task V3 Exception : {}", e.toString());
        }
    }

    @Override
    @Async("v2_executor")
    public Future<String> future() {
        try {
            log.info("Future test Task V4 Run");
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                log.info("Future Task - index {}", i);
            }
        } catch (Exception e) {
            log.error("Future test Task Exception : {}", e.toString());
        }
        // ExecutorService의 submit 메서드는 리턴값을 future로 감싸서 반환
        return new AsyncResult<>("Future test Task V4 End");
    }

}

