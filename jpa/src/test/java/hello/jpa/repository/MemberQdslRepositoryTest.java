package hello.jpa.repository;

import hello.jpa.entity.Member;
import hello.jpa.repository.jpql.MemberRepository;
import hello.jpa.repository.querydsl.MemberQdslRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberQdslRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberQdslRepository memberQdslRepository;

    @Test
    public void findById() {
        Member member1 = new Member("test1", 13);
        Member member2 = new Member("test2", 13);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member savedMember = memberQdslRepository.findById(1L);
        System.out.println(savedMember.toString());
        assertThat(savedMember ).isEqualTo(member1);
    }

}