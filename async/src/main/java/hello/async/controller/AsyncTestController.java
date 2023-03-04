package hello.async.controller;


import hello.async.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/*")
@Slf4j
@RequiredArgsConstructor
public class AsyncTestController {

    private final TestService testService;

    @GetMapping("v1")
    public String asyncTestV1() {
        testService.asyncThreadTestV1();
        return "ok";
    }

    @GetMapping("v2")
    public String asyncTestV2() {
        testService.asyncThreadTestV2();
        return "ok";
    }

    @GetMapping("v3")
    public String asyncTestV3() {
        testService.asyncThreadTestV3();
        return "ok";
    }

    @GetMapping("v4-future")
    public String asyncTestV4() throws Exception {
        final Future<String> futureResult = testService.future();
        /**
         * 비동기 메서드는 비동기로 동작하지만
         * future의 get 메서드는 메서드의 결과를 조회할 때까지 계속 기다리고 결과적으로는 !블로킹! 현상이 발생함
         */
        log.info(futureResult.get());//해당 스레드의 작업이 끝날 때까지 대기하고 끝나면 결과값을 받아온다.
        return "ok";
    }

}
