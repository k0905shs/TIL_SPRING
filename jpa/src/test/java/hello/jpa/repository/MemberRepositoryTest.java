package hello.jpa.repository;

import hello.jpa.entity.Member;
import net.bytebuddy.implementation.bytecode.Throw;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}


