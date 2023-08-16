package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 helloTraceV2;


    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = helloTraceV2.beginSync(traceId,"OrderService.orderItem()");
            orderRepository.save(status.getTraceId(),itemId);
            helloTraceV2.end(status);
        }catch (Exception e){
            helloTraceV2.exception(status,e);
            //예외를 반드시 던저야 한다, 이걸 안해주면 로그 사용단계에서 예외가 종료 되지만. 그러면 로그 때문에 프로그램의 흐름이 망가지기 때문에, 꼭 다시 예외를 던저야 함
            throw e;
        }

    }
}
