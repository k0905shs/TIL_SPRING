package hello.async.controller;


import hello.async.service.TestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/*")
@Slf4j
public class AsyncTestController {

    @Resource(name = "testService")
    private TestService testService;

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



}
