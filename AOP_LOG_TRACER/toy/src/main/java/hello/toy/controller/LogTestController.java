package hello.toy.controller;

import hello.toy.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/log/*")
public class LogTestController {

    private final LogService logService;

//    @LogTrace
    @GetMapping("/test")
    public String test() throws Exception {
        return logService.test();
    }

}
