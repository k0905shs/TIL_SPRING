package hello.proxy.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepositoryV2;

    public void orderItem(String itemId) {
        orderRepositoryV2.save(itemId);
    }
}
