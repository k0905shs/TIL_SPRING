package hello.proxy.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private OrderServiceV3 orderServiceV3;


    @GetMapping("/v3/request")
    String request(@RequestParam("itemId") String itemId){
        orderServiceV3.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v3/no-log")
    String noLog(){
        return "ok";
    }

}
