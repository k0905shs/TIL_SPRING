package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 helloTraceV2;

    @GetMapping("/v2/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = helloTraceV2.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(),itemId);
            helloTraceV2.end(status);
            return "ok";
        }catch (Exception e){
            helloTraceV2.exception(status,e);
            //예외를 반드시 던저야 한다, 이걸 안해주면 로그 사용단계에서 예외가 종료 되지만. 그러면 로그 때문에 프로그램의 흐름이 망가지기 때문에, 꼭 다시 예외를 던저야 함
            throw e;
        }

    }
}
