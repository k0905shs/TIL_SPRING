package hello.jpa.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "teamName"})
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String teamName;

    @OneToMany(mappedBy = "team") //양방향 매핑일 경우 연관관계의 주인에 mapped by를 사용해서 방향을 알려줘야함
    private List<Member> members = new ArrayList<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }
}