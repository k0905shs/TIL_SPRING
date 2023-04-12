package hello.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {

    @Id @GeneratedValue
    private Long id;

    private String teamName;

    @OneToMany
    private List<Member> members = new ArrayList<>();
}
