package hello.jpa.repository;

import hello.jpa.entity.Member;
import hello.jpa.repository.jpql.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /**
     * Page 사용 예제 Page는 0부터 시작함
     */
    @Test
    public void page() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        //when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));
        Page<Member> page = memberRepository.findByAge(10, pageRequest);
        //then
        List<Member> content = page.getContent(); //조회된 데이터
        assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
        assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
        assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
        assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?
    }

    @Test
    public void bulkUpdate() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));
        //when
        int resultCount = memberRepository.bulkAgePlus(20);
        //then
        assertThat(resultCount).isEqualTo(3);
    }
}


