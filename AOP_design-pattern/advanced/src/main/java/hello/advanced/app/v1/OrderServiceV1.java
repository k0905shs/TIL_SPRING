package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 helloTraceV1;


    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = helloTraceV1.begin("OrderService.orderItem()");
            orderRepository.save(itemId);
            helloTraceV1.end(status);
        }catch (Exception e){
            helloTraceV1.exception(status,e);
            //예외를 반드시 던저야 한다, 이걸 안해주면 로그 사용단계에서 예외가 종료 되지만. 그러면 로그 때문에 프로그램의 흐름이 망가지기 때문에, 꼭 다시 예외를 던저야 함
            throw e;
        }

    }
}
