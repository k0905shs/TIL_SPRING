package hello.jpa.repository;

import hello.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJpaRepository {

    @PersistenceContext //스프링 컨테이너가 jpa에 있는 영속성 컨텍스트를 주입한다.
    private EntityManager entityManager;

    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    public Member find(Long memberId) {
        return entityManager.find(Member.class, memberId);
    }

}
