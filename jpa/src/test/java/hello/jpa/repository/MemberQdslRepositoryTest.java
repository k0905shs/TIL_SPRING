package hello.jpa.repository;

import com.querydsl.core.types.Order;
import hello.jpa.dto.MemberProjectionDto;
import hello.jpa.dto.MemberQDto;
import hello.jpa.entity.Member;
import hello.jpa.repository.jpql.MemberRepository;
import hello.jpa.repository.querydsl.MemberQdslRepository;
import hello.jpa.util.OrderCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        assertThat(savedMember).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member("test1", 13);
        Member member2 = new Member("test2", 13);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> memberList = memberQdslRepository.findAll();
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    public void projectionFindAll() {
        Member member1 = new Member("test1", 13);
        Member member2 = new Member("test2", 13);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<MemberProjectionDto.memberC> memberConstructorList = memberQdslRepository.findMemberConstructorP();
        List<MemberQDto> memberQDtoList = memberQdslRepository.findMemberAnnoP();
        assertThat(memberConstructorList.size()).isEqualTo(2);
        assertThat(memberConstructorList.size()).isEqualTo(memberQDtoList.size());
    }

    @Test
    public void dynamicOrderTest() {
        Member member1 = new Member("test1", 5);
        Member member2 = new Member("test2", 4);
        Member member3 = new Member("test3", 3);
        Member member4 = new Member("test4", 2);
        Member member5 = new Member("test5", 1);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        List<Member> memberList = memberQdslRepository.findAllMemberOrderBYDynamic(OrderCondition.AGE, Order.DESC);
        for (Member member : memberList) {
            System.out.println(member.toString());
        }
    }
}