package hello.jpa.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberQDto {
    private String userName;
    private int age;

    @QueryProjection
    public MemberQDto(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }
}
