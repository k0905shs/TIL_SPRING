package hello.advanced.trace;


import lombok.Data;
import lombok.ToString;

import java.util.UUID;

/**
 * 로그 추적기
 * 여기서는 트랜잭션ID와 깊이를 표현하는 level을 묶어서 TraceId 라는 개념을 만들었다.
 * TraceId 는 단순히 id와 level 정보를 함께 가지고 있다
 */
@ToString
public class TraceId {
    private String id;
    private int level;

    public TraceId(){
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level){
        this.id = id;
        this.level = level;
    }

    /**
     * 트레이스 ID 생성기
     */
    private String createId(){
        return UUID.randomUUID().toString().substring(0,8);
    }

    /**
     다음 TraceId 를 만든다. 예제 로그를 잘 보면 깊이가 증가해도 트랜잭션ID는 같다.
     */
    public TraceId createNextId(){
        return new TraceId(this.id,this.level+1);
    }

    /**
     이전 TraceId 를 만든다. 예제 로그를 잘 보면 깊이가 감소해도 트랜잭션ID는 같다.
     */    
    public TraceId createPreviousId(){
        return new TraceId(this.id,this.level-1);
    }

    /**
     *첫 번째 레벨 여부를 편리하게 확인할 수 있는 메서드
     */
    public boolean isFirstLevel(){
        return this.level==0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
