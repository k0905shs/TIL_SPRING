package hello.transaction.service.v3;

import hello.transaction.entity.Member;
import hello.transaction.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionalServiceImpl implements TransactionalService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String transactionalV1() {
        Member member1 = new Member("k1");
        Member member2 = new Member( "k2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        throw new NullPointerException();
//        return "ok";
    }

}
