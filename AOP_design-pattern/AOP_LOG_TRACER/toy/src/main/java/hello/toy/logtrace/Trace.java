package hello.toy.logtrace;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trace {
    private String traceId;
    private Integer level;


    public void nextLevel(){
        this.level++;
    }

    public void prevLevel(){
        this.level--;
    }

    public boolean isLevelOne(){
        return this.level == 1;
    }

}
