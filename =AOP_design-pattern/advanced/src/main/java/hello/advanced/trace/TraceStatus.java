package hello.advanced.trace;

import lombok.ToString;

/**
 * 로그의 상태 정보 저장
 */
@ToString
public class TraceStatus {

    private TraceId traceId;//내부 트랜잭션 id
    private Long startTimeMs;//로그 시작 시간, 이걸 바탕으로 시작 ~ 종료 까지 수행 시간을 구할 수 있다.
    private String message;// 시작시 사용한 메시지

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}

