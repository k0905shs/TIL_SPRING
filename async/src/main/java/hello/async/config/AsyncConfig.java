package hello.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "v1_executor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1); // 기본적으로 실행을 대기하고 있는 Thread의 갯수
        executor.setMaxPoolSize(2); // 동시 동작하는, 최대 Thread 갯수
        executor.setQueueCapacity(1); // MaxPoolSize를 초과하는 요청이 Thread 생성 요청시 해당 내용을 Queue에 저장하게 되고, 사용할수 있는 Thread 여유 자리가 발생하면 하나씩 꺼내져서 동작하게 된다.
        executor.setThreadNamePrefix("test_executor1");// spring이 생성하는 쓰레드의 접두사
        return executor;
    }

    /**
     * setCorePoolSize, setMaxPoolSize, setQueueCapacity 괸계
     * 최초 corePoolSize 만큼 동작하다가 더 이상 처리 할 수 없는 경우 maxPoolSize 만큼 스레드를 증가시키는 것이 아니라 queue에서 task를 대기 시킨 후
     * ● queue가 꽉 차게 되면 그때 max 사이즈만큼 스레드를 생성해서 처리한다.
     */


    @Bean(name = "v2_executor")
    public Executor asyncTaskExecutor2() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(1);
        executor.setThreadNamePrefix("test_executor2");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // Reject Policy 아래 주석 참조
        executor.setAwaitTerminationSeconds(60); // 최대 종료 대기 시간을 설정할 수 있다.
        return executor;
    }
}

    /**
     * Reject Policy
     * Executor는 작업 큐가 꽉 찼을 때 아래 4가지 전략 중에 하나를 선택해서 사용할 수 있다.
     *  1.ThreadPoolExecutor.AbortPolicy        -> Default, Reject된 task가 RejectedExecutionException을 던진다.
     *  2.ThreadPoolExecutor.CallerRunsPolicy   -> 호출한 Thread에서 reject된 task를 대신 실행한다.
     *  3.ThreadPoolExecutor.DiscardPolicy      -> Reject된 task는 버려진다. Exception도 발생하지 않는다.
     *  4.ThreadPoolExecutor.DiscardOldestPolicy-> 실행자를 종료하지 않는 한 가장 오래된 처리되지 않은 요청을 삭제하고 execute()를 다시 시도한다.
     */