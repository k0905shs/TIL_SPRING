package hello.advanced.trace.threadLocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    /**
     * 동시성 문제 확인 테스트
     */
    @Test
    void field() {
        log.info("main start");
        
        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        threadA.start(); //A실행

//        sleep(2000); //동시성 문제 발생X
        /**
         * 출력 결과
         * 12:51:40.408 [main] INFO hello.advanced.trace.threadLocal.FieldServiceTest - main start
         * 12:51:40.412 [thread-A] INFO hello.advanced.trace.threadLocal.FieldService - 저장 name=userA -> nameStore=null
         * 12:51:41.430 [thread-A] INFO hello.advanced.trace.threadLocal.FieldService - 조회 nameStore=userA
         * 12:51:42.417 [thread-B] INFO hello.advanced.trace.threadLocal.FieldService - 저장 name=userB -> nameStore=userA
         * 12:51:43.431 [thread-B] INFO hello.advanced.trace.threadLocal.FieldService - 조회 nameStore=userB
         * 12:51:45.419 [main] INFO hello.advanced.trace.threadLocal.FieldServiceTest - main exit
         */


        sleep(100); //동시성 문제 발생
        /**
         * 출력 결과
         * 12:52:21.980 [main] INFO hello.advanced.trace.threadLocal.FieldServiceTest - main start
         * 12:52:21.983 [thread-A] INFO hello.advanced.trace.threadLocal.FieldService - 저장 name=userA -> nameStore=null
         * 12:52:22.093 [thread-B] INFO hello.advanced.trace.threadLocal.FieldService - 저장 name=userB -> nameStore=userA
         * 12:52:23.008 [thread-A] INFO hello.advanced.trace.threadLocal.FieldService - 조회 nameStore=userB
         * 12:52:23.102 [thread-B] INFO hello.advanced.trace.threadLocal.FieldService - 조회 nameStore=use
         */


        threadB.start(); //B실행
        sleep(3000); //메인 쓰레드 종료 대기
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
