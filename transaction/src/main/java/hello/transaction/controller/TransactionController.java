package hello.transaction.controller;

import hello.transaction.service.v1.TMService;
import hello.transaction.service.v2.AspectTxService;
import hello.transaction.service.v3.TransactionalService;
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
    private final TransactionalService transactionalService;

    @GetMapping("v1-template")
    public String v1Template() throws Exception {
        tmService.templateMethod();
        return "ok";
    }


    @GetMapping("v2-aspect")
    public String v2Aspect() throws Exception {
        aspectTxService.aspectTxV1();
        return "ok";
    }

    @GetMapping("v3-transactional")
    public String v3Transactional() throws Exception {
        transactionalService.transactionalV1();
        return "ok";
    }


}
