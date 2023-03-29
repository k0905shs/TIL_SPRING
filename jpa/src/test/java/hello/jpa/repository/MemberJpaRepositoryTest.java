package hello.jpa.repository;

import hello.jpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SpringBootTest에 Transactional이 있으면 해당 메소드 종료 후 전체 롤백함!
 */
@SpringBootTest
@Transactional //jpa의 모든 데이터 저장은 트랜잭션 안에서 일어난다.
//@Rollback(value = false) //해당 어노테이션 사용시 테스트 종료 해도 롤백 안함
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("test1", 15);
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());

        assertThat(findMember).isEqualTo(member); //findMember == member
        //jpa 특성상 같은 트랙잭션에서는 영속성 컨텍스트의 동일성을 보장 (같은 인스턴스)
        //트랜잭션이 다르면 다른 인스턴스
        //1차 캐시
    }

}