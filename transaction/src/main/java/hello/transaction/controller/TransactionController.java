package hello.transaction.controller;

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

    @GetMapping("all-member")
    public void findAllMember() {
        aspectTxService.aspectTxV1();
    }

}
