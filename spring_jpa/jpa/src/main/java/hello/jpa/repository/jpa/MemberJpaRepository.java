package hello.jpa.repository.jpa;

import hello.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 순수 jpa로 작성한 repository
 */
@Repository
public class MemberJpaRepository {

    @PersistenceContext //스프링 컨테이너가 jpa에 있는 영속성 컨텍스트를 주입한다.'
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    public void delete(Member member) {
        em.remove(member);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
