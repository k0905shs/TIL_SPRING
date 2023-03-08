package hello.transaction.service.v2;

import hello.transaction.entity.Member;
import hello.transaction.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AspectTxServiceImpl implements AspectTxService{

    private final MemberRepository memberRepository;


    @Override
    public void aspectTxV1() {
        Member member1 = new Member("k1");
        Member member2 = new Member( "k2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        throw new NullPointerException();
    }
}
