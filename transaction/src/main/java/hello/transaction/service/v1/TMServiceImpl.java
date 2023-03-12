package hello.transaction.service.v1;

import hello.transaction.entity.Member;
import hello.transaction.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

@Service
public class TMServiceImpl implements TMService{
    /**
     * 스프링 프레임워크에서 제공하는 프로그래밍 방식 트랜잭션 관리는 두 가지가 있다.
     * 1. TransactionTemplate 이용
     * 2. PlatformTransactionManager 구현체 직접 사용
     *
     * 스프링 팀은 프로그래밍 방식 트랜잭션 관리에 있어 TransactionTemplate 사용을 권장한다.
     * 두 번째 방식은 예외 핸들링이 더 가볍다는 점 외엔 JTA UserTransaction API 를 사용하는 것과 유사하다.
     */
    private final TransactionTemplate transactionTemplate; // TransactionTemplate
    private final MemberRepository memberRepository;

    public TMServiceImpl(PlatformTransactionManager transactionManager, MemberRepository memberRepository) {
        Assert.notNull(transactionManager, "transactionManager argument must not be null.");
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    @Override//doInTransaction 메소드 안에 로직을 구현해서 사용한다.
    public Object templateMethod() {
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                Member member1 = new Member("k1");
                Member member2 = new Member( "k2");

                memberRepository.save(member1);
                memberRepository.save(member2);
                throw new NullPointerException();
//                return "ok";
            }
        });
    }

    @Override
    public void platformMethod() {

    }


    /**
     * 트랜잭션 작업이 많지 않다면 프로그래밍 방식은 대개 좋은 선택이 된다.
     * 예를 들어, 트랜잭션 작업은 몇 개의 update 가 전부인 웹 어플레케이션을 개발한다면 스프링이나 다른 기술을 사용한 트랜잭션 프록시 설정을 원치 않을 수 있다.
     * 이런 경우에 TransactionTemplate 은 좋은 처리 방법이다. 명시적으로 트랜잭션 이름을 세팅하는 일은 오직 프로그래밍 방식 트랜잭션 관리에서만 할 수 있는 일이기도 하다.
     *
     */
}
