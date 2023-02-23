package hello.async.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Slf4j
@Service("testService")
public class TestService {

    @Resource(name = "v1_executor")
//    private Executor executor1; //JAVA concurrent Executor
    private ThreadPoolTaskExecutor executor1; //ThreadPoolTaskExecutor 추가 메소드 사용 가능

    @Async //선언한 빈을 주입 받아서 사용하는 방법
    public void asyncThreadTestV1() { 
        Runnable runnable = () -> { // Runnable 매게변수 구현
            try {
                log.info("Thread Task V1 Run");
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(500);
                    log.info("{} - index {}", executor1.getThreadNamePrefix(), i);
                }
                log.info("Thread Task V1 End");
            } catch (Exception e) {
                log.error("Thread Task V1 Exception : {}", e.toString());
            }
        };
        executor1.execute(runnable);
    }

    @Async("v2_executor") //어노테이션에 스레드 풀 네임 사용
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
}

