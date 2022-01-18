package hello.proxy.pureproxy.proxy.code;

import org.junit.jupiter.api.Test;

public class ProxyPatternTest {
    /**
     * 메소드를 호출하여 얻은 데이터가 만약 변하지 않는 데이터 라면, 어딘가에 데이터를 보관해 두고 이미 조회한 데이터를 사용하는 것이
     * 성능상 좋다. 이런 것을 캐시 라고 한다.
     *
     * 프록시 패턴의 주요 기능은 접근제어이다. 캐시도 접근 자체를 제어하는 기능중 하나이다
     */
    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();

    }

    /**
     * realSubject 와 cacheProxy 를 생성하고 둘을 연결한다. 결과적으로 cacheProxy 가 realSubject 를
     * 참조하는 런타임 객체 의존관계가 완성된다. 그리고 마지막으로 client 에 realSubject 가 아닌
     * cacheProxy 를 주입한다. 이 과정을 통해서 client -> cacheProxy -> realSubject 런타임 객체
     * 의존 관계가 완성된다.
     */
    @Test
    void cacheProxyTest() {
        Subject realSubject = new RealSubject();
        Subject cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }


}
