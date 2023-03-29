package hello.jpa.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Team {

    @Id @GeneratedValue
    private Long id;

    private String Name;

//    private List<Member> members;

}
