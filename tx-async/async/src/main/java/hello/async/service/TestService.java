package hello.async.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface TestService {
    /**
     * v1. 선언한 빈을 주입 받아서 사용하는 방법
     */
    void asyncThreadTestV1();

    /**
     * v2. Async 어노테이션에 빈 사용할 빈 네임 사용
     */
    void asyncThreadTestV2();

    /**
     * AsyncConfigurer인터페이스의 getAsyncExecutor()를 구현하여 사용하는 방식
     */
    void asyncThreadTestV3();

    /**
     * 반환타입 future
     */
    Future<String> future();

    /**
     * v1 태스크가 실행중인지 확인
     */
    boolean isRunV1();

    int countV1RemainTask();
}
