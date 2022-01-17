package hello.advanced.trace.threadLocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService service = new ThreadLocalService();

    /**
     * 출력 결과
     * 13:23:32.583 [main] INFO hello.advanced.trace.threadLocal.ThreadLocalServiceTest - main start
     * 13:23:32.586 [thread-A] INFO hello.advanced.trace.threadLocal.ThreadLocalService - 저장 name=userA -> nameStore=null
     * 13:23:32.699 [thread-B] INFO hello.advanced.trace.threadLocal.ThreadLocalService - 저장 name=userB -> nameStore=null
     * 13:23:33.591 [thread-A] INFO hello.advanced.trace.threadLocal.ThreadLocalService - 조회 nameStore=userA
     * 13:23:33.701 [thread-B] INFO hello.advanced.trace.threadLocal.ThreadLocalService - 조회 nameStore=userB
     * 13:23:34.710 [main] INFO hello.advanced.trace.threadLocal.ThreadLocalServiceTest - main exit
     */
    @Test
    void threadLocal() {
        log.info("main start");
        Runnable userA = () -> {
            service.logic("userA");
        };
        Runnable userB = () -> {
            service.logic("userB");
        };
        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        threadA.start();
        sleep(100);
        threadB.start();
        sleep(2000);
        log.info("main exit");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
