package hello.jpa.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberProjectionDto {

    @NoArgsConstructor
    @Getter @Setter
    public static class memberC { //생성자 사용 Projections 샘플 예시
        private String name; //새성자 사용시 굳이 Member Entity와 필드명이 같을 필요 없음
        private int age;

        public memberC(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Data
    @NoArgsConstructor
    public static class memberB { //Bean(Reflection) Projections 샘플 예시
        private String userName; //Bean 사용시 MemberEntity와 필드명이 같아야 한다.
        private int age;
    }
    @Data
    @NoArgsConstructor
    public static class memberF { //Bean(Reflection) Projections 샘플 예시
        private String userName; //Bean 사용시 MemberEntity와 필드명이 같아야 한다.
        private int age;
    }
}

