package hello.jpa.repository;

import hello.jpa.entity.Member;
import hello.jpa.repository.jpql.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void memberRepositoryTest() {
        Member member = new Member("test", 1);
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(1L).orElseThrow();

        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
    }

    @Test
    public void memberRepoFindByUserNameAndAge() {
        Member member0 = new Member("test1", 11);
        Member member1 = new Member("test1", 100);
        Member member2 = new Member("test1", 200);
        Member member3 = new Member("test2", 100);

        memberRepository.save(member0);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> memberList = memberRepository.findByUserNameAndAgeGreaterThan("test1", 50);
        for (Member member : memberList) {
            System.out.println(member.getUserName()+" : "+ member.getAge());
        }
        assertThat(memberList.size()).isEqualTo(2);
    }
}


