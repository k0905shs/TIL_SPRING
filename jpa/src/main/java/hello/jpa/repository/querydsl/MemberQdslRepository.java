package hello.jpa.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.jpa.entity.Member;
import hello.jpa.entity.QMember;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * Querydsl은 Member와 동일한 패키지에 QMember라는 이름을 가진 쿼리 타입을 생성한다.
 * Querydsl 쿼리에서 Member 타입을 위한 정적 타입 변수로 QMember를 사용한다.
 * ref : http://querydsl.com/static/querydsl/4.0.1/reference/ko-KR/html_single/#d0e1030
 */
@Repository
public class MemberQdslRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberQdslRepository(EntityManager entityManager) {
        super(QMember.class);
        super.setEntityManager(entityManager);
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    // findById 예시
    public Member findById(Long memberId) {
        QMember member = QMember.member; // 기본 인스턴스 사용
//        QMember member = new QMember("m"); // 인스턴스 직접 지정 -> 조인을 하거나 같은 엔티티를 서브쿼리에 사용하면 다음 처럼 직접 지정해서 사용한다.

        //QueryDsl FindById 구문 예제 각 메소드 기능들은 ref 꼭 볼 것!!
        return jpaQueryFactory.selectFrom(member) //select하는 엔티티와 from의 엔티티가 일치할 경우 합칠 수 있다.
                .where(member.id.eq(memberId))
                .fetchFirst();
    }
}