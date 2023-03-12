package hello.transaction.controller;

import hello.transaction.service.v1.TMService;
import hello.transaction.service.v2.AspectTxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tx/*")
@RequiredArgsConstructor
public class TransactionController {

    private final AspectTxService aspectTxService;
    private final TMService tmService;

    @GetMapping("v1-template")
    public void v1Template() {
        tmService.templateMethod();
    }


    @GetMapping("v2-aspect")
    public void v2Aspect() {
        aspectTxService.aspectTxV1();
    }

}
