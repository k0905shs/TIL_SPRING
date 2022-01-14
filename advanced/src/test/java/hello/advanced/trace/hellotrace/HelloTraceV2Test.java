package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class HelloTraceV2Test {

    @Test
    void begin_end_v2(){
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status =trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status.getTraceId(),"hello2");
        trace.end(status2);
        trace.end(status);
    }

    @Test
    void begin_exception_v2(){
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status =trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status.getTraceId(),"hello2");
        trace.exception(status2,new NullPointerException());
        trace.exception(status,new NullPointerException());
    }
}