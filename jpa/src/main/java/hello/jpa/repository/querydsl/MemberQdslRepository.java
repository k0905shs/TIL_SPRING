package hello.jpa.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.jpa.dto.MemberDto;
import hello.jpa.dto.MemberProjectionDto;
import hello.jpa.dto.MemberQDto;
import hello.jpa.dto.QMemberQDto;
import hello.jpa.entity.Member;
import hello.jpa.entity.QMember;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hello.jpa.entity.QMember.member;
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

    /**
     * fetch() :  리스트로 결과를 반환. (만약에 데이터가 없으면 빈 리스트를 반환.)
     * fetchOne() : 단건 조회 (결과가 없을때는 null 을 반환하고 둘 이상일 경우에는 NonUniqueResultException)
     * fetchFirst() : 처음의 한건 반환.
     * fetchResults() : 해당 내용은 페이징을 위해 사용. 페이징을 위해서 total contents를 가져온다.
     * fetchCount() : count 쿼리를 날릴 수 있다.
     */
    public List<Member> findAll() {
        // 기본 인스턴스로 QMember 사용시 static으로 사용 가능하다.
        return jpaQueryFactory.selectFrom(member)
                .fetch();
    }

    /**
     * Projections
     * 엔티티와 다른 반환 타입인 경우 Projections를 사용한다.
     *
     * Projections 사용 방법
     * 1. Projections.constructor
     * 2. Projections.bean
     * 3. Field 직접 조회
     * 4. @QueryProjection
     */

    /**
     * 1. Projections.constructor
     */
    public List<MemberProjectionDto.memberC> findMemberConstructorP() {
        return jpaQueryFactory.select(Projections.constructor(MemberProjectionDto.memberC.class,
                        member.userName, member.age)) // 생성자의 경우 type만 맞으면됨 즉 필드명이 달라도 상관이 없다
                .from(member)
                .fetch();
    }

    /**
     * 2. Projections.bean
     * .as("age") 처럼 alias를 사용해 이름을 맞출 수 도 있음
     */
    public List<MemberProjectionDto.memberB> findMemberBeanP() {
        return jpaQueryFactory.select(Projections.bean(MemberProjectionDto.memberB.class,
                        member.userName, member.age.as("age"))) // 이게 dto 필드명이랑 정확히 매칭 되어야 쓸수 있음
                .from(member)
                .fetch();
    }
    /**
     * 3. Projections.fields
     */
    public List<MemberProjectionDto.memberF> findMemberFieldP() {
        return jpaQueryFactory.select(Projections.fields(MemberProjectionDto.memberF.class,
                        member.userName, member.age)) // 이게 dto 필드명이랑 정확히 매칭 되어야 쓸수 있음
                .from(member)
                .fetch();
    }

    /**
     * 4.@QueryProjection
     *  dto도 Q 파일로 만들어서 사용할수 가 있다.
     * 만들고자하는 생성자에 @QueryProjection을 달아주고 !!컴파일!!해주면 Q타입이 생성된다.
     */
    public List<MemberQDto> findMemberAnnoP() {
        return jpaQueryFactory.select(new QMemberQDto(member.userName, member.age))
                .from(member)
                .fetch();
    }
}

