package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

/**
 부모 클래스에 알고리즘의 골격인 템플릿을 정의하고, 일부 변경되는 로직은 자식 클래스에 정의하는
 것이다. 이렇게 하면 자식 클래스가 알고리즘의 전체 구조를 변경하지 않고, 특정 부분만 재정의할 수 있다.
 결국 상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것이다.
 */
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace){
        this.trace = trace;
    }

    public T execute(String message){
        TraceStatus status = null;

        try{
            status = trace.begin(message);

            //핵심 로직 호출
            T result = call();

            trace.end(status);
            return  result;
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();

}
