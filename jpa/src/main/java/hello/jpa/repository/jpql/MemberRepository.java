package hello.jpa.repository.jpql;

import hello.jpa.dto.MemberDto;
import hello.jpa.entity.Member;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring-data-jpa 를 사용한 repository
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);

    @Query("select m from Member m where m.userName= :userName and m.age = :age")
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);

    /**
     * DTO로 직접 조회
     */
    @Query("select new hello.jpa.dto.MemberDto(m.id, m.userName, t.teamName) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    /**
     * 파라미터 바인딩
     * select m from Member m where m.userName = ?0 //위치 기반
     * select m from Member m where m.userName = :name //이름 기반
     * !! 가독성을 위해 이름 기반으로 사용하는 것이 좋음
     */
    @Query("select m from Member m where m.userName = :name")
    Member findMembers(@Param("name") String userName);

    /**
     * 컬렉션 파라미터 바인딩
     */
    @Query("select m from Member m where m.userName in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    /**
     * 반환 타입
     * List<Member> findByUsername(String name); //컬렉션
     * Member findByUsername(String name); //단건
     * Optional<Member> findByUsername(String name); //단건 Optional
     *
     * 컬렉션 : 결과 없음 -> 빈 컬렉션 반환
     *
     * 단건 : 결과 없음: null 반환
     *       결과가 2건 이상 : NonUniqueResultException 예외 발생
     */


    /**
     * org.springframework.data.domain.Sort : 정렬 기능
     * org.springframework.data.domain.Pageable : 페이징 기능 (내부에 Sort 포함)
     * Page는 0부터 시작함
     */
    Page<Member> findByUserName(String userName, Pageable pageable); //count 쿼리 사용

//    Slice<Member> findByUsername(String userName, Pageable pageable); //count 쿼리 사용안함
//    List<Member> findByUsername(String userName, Pageable pageable); //count 쿼리 사용안함

    List<Member> findByUserName(String userName, Sort sort);

    Page<Member> findByAge(int age, Pageable pageable);

    /**
     * 벌크성 수정 쿼리
     * 벌크성 수정, 삭제 쿼리는 @Modifying 어노테이션을 사용
     * 벌크성 쿼리를 실행하고 나서 영속성 컨텍스트 초기화: @Modifying(clearAutomatically = true)
     * 벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 때문에, 영속성 컨텍스트에 있는 엔티티의 상태와
     * DB에 엔티티 상태가 달라질 수 있다
     * !!!! 따라서 영속성 컨텍스트에 엔티티가 없는 상태에서 벌크 연산을 먼저 실행한다
     */
    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    /**
     * JPQL 페치 조인
     * 사실상 페치 조인(FETCH JOIN)의 간편 버전
     * LEFT OUTER JOIN 사용
     */
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    //공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    //메서드 이름으로 쿼리에서 특히 편리하다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUserName(String userName);

}

