package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 핵심은 기존 코드에 변화 없이 새로 만들어진 기능을 추가하는 것~
 */
@Slf4j
public class CacheProxy implements Subject{

    /**
     * private Subject target : 클라이언트가 프록시를 호출하면 프록시가 최종적으로 실제 객체를
     * 호출해야 한다. 따라서 내부에 실제 객체의 참조를 가지고 있어야 한다. 이렇게 프록시가 호출하는 대상을
     * target 이라 한다.
     */
    private Subject target;

    private String cacheValue;

    public CacheProxy(Subject target){
        this.target = target;
    }

    /**
     *private Subject target : 클라이언트가 프록시를 호출하면 프록시가 최종적으로 실제 객체를
     * 호출해야 한다. 따라서 내부에 실제 객체의 참조를 가지고 있어야 한다. 이렇게 프록시가 호출하는 대상을
     * target 이라 한다.
     */
    @Override
    public String operation() {
        log.info("프록시 호출");
        if(cacheValue == null){
            cacheValue = target.operation();
        }
        return cacheValue;
    }

}
