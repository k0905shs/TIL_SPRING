package hello.jpa.repository.jpql;

import hello.jpa.dto.MemberDto;
import hello.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

}

