package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@ResponseBody
public class OrderControllerV1 {

    private OrderServiceV1 orderServiceV1;

    public OrderControllerV1(OrderServiceV1 orderServiceV1) {
        this.orderServiceV1 = orderServiceV1;
    }

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId){
        orderServiceV1.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v1/no-log")
    String noLog(){
        return "ok";
    }

}
