package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(AspectV6Advice.class)
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * AOP프록시가 적용 되었는지 확인
     */
    @Test
    void aopInfo() {
        log.info("is AopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("is AopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }


    @Test
    void success(){
        orderService.orderItem("itemA");
    }

    @Test
    void exception(){
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex")).isInstanceOf(IllegalStateException.class);
    }




}
