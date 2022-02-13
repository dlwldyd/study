package springdata.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import springdata.datajpa.dto.MemberDto;
import springdata.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    //find(...)By -> ...은 적든 안적든 필드명을 적든 모든 엔티티 반환
    List<Member> findTop3UsernameBy();

    //쿼리 생성 순서
    /*
    JpaRepository<Member, Long>의 타입에 해당하는 타입을 가지고 Member.findByUsernameAAA 라는 named query 가
    있는지 찾아보고 없으면 메서드 생성 규칙에 따라 메서드를 생성한다. 그러고도 안되면 에러 발생
     */
    List<Member> findByUsernameAAA(@Param("username") String username);

    //이름없는 namedQuery 라 할 수 있다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findMember(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new springdata.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); //컬렉션 반환
    //단건 반환, 결과가 하나인게 보장되야함, 2개 이상이면 IncorrectResultSizeDataAccessException 예외터짐
    //JPA 에서는 NonUniqueResultException 이 터지지만(getSingleResult) 스프링 데이터 JPA 가 스프링의 예외로 바꿔서 반환
    //jpa 에서는 결과가 없으면 NoResultException 예외가 터지지만 스프링 데이터 jpa 는 null 로 반환해줌
    Member findMemberByUsername(String username);
    Optional<Member> findOptionalByUsername(String username); // 단건 조회를 Optional 로 감싸서 반환

    //페이징이 필요하고 전체 데이터의 row 수나 페이지수가 얼만지 알아야 할 때 사용한다.
    //@Query 어노테이션을 이용해 카운트 쿼리를 최적화 하기 위한 방법을 제공한다.
    //select count(m) from Member m left join m.team t where m.age = :age -> select count(m) from Member m where m.age = :age
    @Query(value = "select m from Member m left join m.team t where m.age = :age",
            countQuery = "select count(m) from Member m where m.age = :age")
    Page<Member> findPageByAge(@Param("age") int age, Pageable pageable);

    //페이징이 필요하지만 전체 데이터의 row 수나 페이지수가 얼만지 알 필요가 없을 때 사용한다.
    Slice<Member> findSliceByAge(int age, Pageable pageable);

    //페이징이 필요없고 그냥 데이터만 일정크기로 잘라서 가져오고 싶을 때 사용한다.
    List<Member> findListByAge(int age, Pageable pageable);

    //@Modifying 이 있어야 executeUpdate()를 실행한다. 없으면 getResultList 나 getSingleResult 같은거를 실행
    //벌크성 쿼리 후 추가적인 작업을 한다면 clearAutomatically = true 를 설정해두자(벌크성 쿼리 후 em.clear()해줌)
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Override
    //attributePaths 에 들어간 값을 페치조인 해줌(left outer join 이고 다른 조인으로 바꿀 수 없음)
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //@QueryHint(name = "org.hibernate.readOnly", value = "true") 를 설정하면 스냅샷을 만들지 않아 더티 체킹 불가능
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    List<Member> findLockByUsername(String username);

    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findUsingNativeQueryByUsername(String username);
}
