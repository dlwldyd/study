package orm.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import orm.querydsl.dto.MemberDto;
import orm.querydsl.dto.QMemberDto;
import orm.querydsl.dto.UserDto;
import orm.querydsl.entity.Member;
import orm.querydsl.entity.QMember;
import orm.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static orm.querydsl.entity.QMember.*;
import static orm.querydsl.entity.QTeam.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    @Autowired
    EntityManagerFactory emf;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPQL() {
        String qlString =
                "select m from Member m " +
                        "where m.username = :username";
        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
//        QMember m = new QMember("m"); -> 같은 테이블을 조인하는 경우에는 써야할 수도 있음
//        QMember m = QMember.member;

        //select m from Member m where m.username = 'member1'
        Member findMember = queryFactory
                .select(member) // select(member).from(member) == selectFrom(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {

        //select m from Member m where m.username = 'member1' and m.age = 10
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    void searchAndParam() {

        //select m from member m where m.username = 'member1' and m.age = 10
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.eq(10)
                )
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    void resultFetchTest() {

        //getResultList
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        //getSingleResult
        Member fetchOne = queryFactory
                .selectFrom(member)
                .fetchOne();

        // .fetchFirst() == .limit(1).fetchOne()
        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst();
    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
     */
    @Test
    void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        //select m from Member m where m.age = 100 order by m.age desc order by m.username asc nulls last
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc())
                .orderBy(member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    void paging() {

        //select m from Member m order by m.username desc, setFirstResult(0), setMaxResults(2)
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(0)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    /**
     * JPQL이 제공하는 모든 집합함수 제공
     */
    @Test
    void aggregation() {

        //select count(m), sum(m.age), avg(m.age), max(m.age), min(m.age) from Member m
        Tuple tuple = queryFactory
                .select(member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min())
                .from(member)
                .fetchOne();

        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */
    @Test
    void groupBy() {

        //select t.name, avg(m.age) from Member m join m.team t group by t.name having t.name like 'team%'
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .having(team.name.startsWith("team"))
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    /**
     * teamA 에 소속된 모든 회원
     */
    @Test
    void join() {

        //select m from Member m join m.team t where t.name = 'teamA'
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();


/* alias 를 바꾸고 싶을 때

        QTeam hello = new QTeam("hello");

        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, hello)
                .where(hello.name.eq("teamA"))
                .fetch();
 */

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    /**
     * 회원이름과 팀 이름이 같은 회원 조회
     */
    @Test
    void theta_join() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        //select m from Member m, Team t where m.username = t.name
        List<Member> result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'teamA'
     * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='teamA'
     */
    @Test
    void join_on_filtering() {

        //select m, t from Member m left join m.team t on t.name = 'teamA'
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /**
     * 회원이름과 팀 이름이 같은 회원 조회(외부 조인)
     */
    @Test
    void join_on_no_relation() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        //select m, t from Member m left join Team t on t.name = m.username
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team)
                .on(team.name.eq(member.username))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void fetchJoinNo() {
        em.flush();
        em.clear();

        // select m from Member m where m.username = 'member1'
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())).isFalse();
    }

    @Test
    void fetchJoin() {
        em.flush();
        em.clear();

        // select m from Member m join fetch m.team t where m.username = 'member1'
        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())).isTrue();
    }

    /**
     * 나이가 가장 많은 회원을 조회
     */
    @Test
    void subQuery() {

        QMember member2 = new QMember("member2");

        //select m from Member m where m.age = (select max(m2.age) from Member m2)
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(JPAExpressions.
                        select(member2.age.max())
                                .from(member2)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(40);
    }

    /**
     * 나이가 평균 이상인 회원
     */
    @Test
    void subQuery2() {

        QMember member2 = new QMember("member2");

        //select m from Member m where m.age >= (select avg(m2.age) from Member m2)
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(JPAExpressions.
                        select(member2.age.avg())
                                .from(member2)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(30, 40);
    }

    @Test
    void subQueryIn() {

        QMember member2 = new QMember("member2");

        //select m from Member m where m.age in (select m2.age from Member m2 where m2.age > 10)
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(JPAExpressions.
                        select(member2.age)
                                .from(member2)
                                .where(member2.age.gt(10))
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(20, 30, 40);
    }

    /*
    JPA 는 where 절, select 절(하이버네이트 사용 시)에서는 서브쿼리를 지원하지만 from 절에서는 서브쿼리를 지워하지 않기
    때문에 Querydsl 도 from 절의 서브쿼리를 지원하지 않는다.
     */
    @Test
    void selectSubQuery() {

        QMember member2 = new QMember("member2");

        //select m.username, (select avg(m2.age) from Member m2) from Member m
        List<Tuple> result = queryFactory
                .select(member.username,JPAExpressions.
                        select(member2.age.avg())
                                .from(member2))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    //간단한 조건일 때
    @Test
    void simpleCase() {

        /*
        select
            case
                when m.age = 10 then '열살'
                when m.age = 20 then '스무살'
                else '기타'
            end
        from Member m
         */
        List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    //복잡한 조건일 때
    @Test
    void complexCase() {

        /*
        select
            case
                when (m.age between 0 and 20) then '0~20살'
                when (m.age between 20 and 30) then '20~30살'
                else '기타'
            end
        from Member m
         */
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(20, 30)).then("20~30살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void caseInOrderBy() {

        /*
        select
            m.username,
            m.age,
            case
                when (m.age between 0 and 20) then 2
                when (m.age between 21 and 30) then 1
                else 3
            end
        from
            Member m
        order by
            case
                when (m.age between 0 and 20) then 2
                when (m.age between 21 and 30) then 1
                else 3
            end desc
         */
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(member.age.between(0, 20)).then(2)
                .when(member.age.between(21, 30)).then(1)
                .otherwise(3);

        List<Tuple> result = queryFactory
                .select(member.username, member.age, rankPath)
                .from(member)
                .orderBy(rankPath.desc())
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            Integer rank = tuple.get(rankPath);
            System.out.println("username = " + username + " age = " + age + " rank = " + rank);
        }
    }

    @Test
    void constant() {

        //select m.username from Member m
        //상수는 쿼리결과 받은 후 붙여짐
        List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void concat() {

        //select concat(concat(m.username, '_'), m.age) from Member m
        //concat 은 String 타입만 적용되기 때문에 StringValue() 로 String 타입으로 바꿔줘야함
        //jpql 상으로는 int 타입으로 String 타입으로 캐스팅 하지 않지만 sql 상으로는 integer 를 char 로 캐스팅 해줌
        //stringValue()는 enum 타입을 처리할 때 자주 사용
        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue())) //{username}_{age}
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void simpleProjection() {
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    /*
    Tuple 은 QueryDsl 종속적인 클래스이기 때문에 Repository 계층에서만 쓰고 Service 계층이나 Controller 계층에는
    DTO 로 변환해서 넘겨주는 것이 좋다.(DB 관련 기술을 바꿀 일이 있을 때 Repository 계층으로만 변화를 한정할 수 있다.)
     */
    @Test
    void tupleProjection() {
        List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }

    @Test
    void findDtoByQuerydslWithSetter() {

        //select new orm.querydsl.dto.MemberDto(m.username, m.age) from Member m
        //new MemberDto()로 생성한 후 setter 로 필드값을 초기화(NoArgsConstructor, setter 필요)
        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class, //bean 은 자바빈(getter, setter)을 뜻함
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findDtoByQuerydslWithFields() {

        //select new orm.querydsl.dto.MemberDto(m.username, m.age) from Member m
        //필드값에 직접 값을 주입(필드가 private 라도 reflection 을 통해 직접 주입 가능)
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findDtoByQuerydslWithConstructor() {

        //select new orm.querydsl.dto.MemberDto(m.username, m.age) from Member m
        //new MemberDto(m.username, m.age)로 객체 생성하면서 초기화
        List<MemberDto> result = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findUserDtoByQuerydsl() {

        QMember member2 = new QMember("member2");
        /*
        필드로 주입해 초기화할 때는 필드명을 들어간 파라미터의 이름으로 판단해 주입한다. UserDto 에는 필드이름이 username 이
        아니라 name 인데 파라미터로는 member.username 이 들어갔기 때문에 만약 as()를 통해 alias 를 변경하지 않는다면
        UserDto 의 name 에는 null 값이 들어간다.
         */
        List<UserDto> result1 = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name"),
                        member.age))
                .from(member)
                .fetch();

        //setter 를 통해 초기화할 때도 setter 이름과 파라미터 이름으로 판단하기 때문에 as()로 alias 를 변경해야한다.
        //서브쿼리에 alias 를 줘야할 때는 ExpressionUtils.as()를 통해 줄 수 있다.
        List<UserDto> result2 = queryFactory
                .select(Projections.bean(UserDto.class,
                        member.username.as("name"),
                        ExpressionUtils.as(JPAExpressions
                                .select(member2.age.max())
                                .from(member2), "age")
                ))
                .from(member)
                .fetch();

        for (UserDto userDto : result1) {
            System.out.println("userDto = " + userDto);
        }
        System.out.println(" =================================================== ");
        for (UserDto userDto : result2) {
            System.out.println("userDto = " + userDto);
        }
    }

    @Test
    void findDtoByQueryProjection() {

        //constructor 와 달리 컴파일 시점에 오류를 잡을 수 있다.
        //DTO 가 Querydsl 라이브러리에 의존적이게 된다.
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void dynamicQuery_booleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember1(usernameParam, ageParam);
//        List<Member> result = searchMember1(null, null);
        assertThat(result.size()).isEqualTo(1);
    }

    //파라미터가 null 이면 where 문의 조건으로 들어가지 않음
    private List<Member> searchMember1(String usernameCond, Integer ageCond) {

        //new BooleanBuilder(member.username.eq(usernameCond)) 처럼 초기조건을 넣을 수도 있음
        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) {
            //빌더에 .and(member.username.eq(usernameCond))가 들어감
            //빌더에 초기 조건이 아무것도 없을 때 들어가면 member.username.eq(usernameCond)가 들어감
            builder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder) //builder.or(member.username.eq("member10"))처럼 builder 뒤에도 조건을 덧붙일 수 있음
                .fetch();
    }

    @Test
    void dynamicQuery_whereParam() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);
//        List<Member> result = searchMember2(null, null);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {

        //조건이 null 일 경우 무시됨
        return queryFactory
                .selectFrom(member)
//                .where(usernameEq(usernameCond), ageEq(ageCond))
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    //BooleanBuilder 처럼 조립하기 위해 BooleanExpression 으로 반환하는 것이 좋다.
    private BooleanExpression usernameEq(String usernameCond) {
        if (usernameCond == null) {
            return null;
        }
        return member.username.eq(usernameCond);
    }

    private BooleanExpression ageEq(Integer ageCond) {
        if (ageCond == null) {
            return null;
        }
        return member.age.eq(ageCond);
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        if (usernameCond == null && ageCond == null) {
            return null;
        }
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    void bulkUpdate() {

        //update Member m set m.username = '비회원' where m.age < 28
        long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        em.clear();

        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void bulkAdd() {

        //update Member m set m.age = m.age + 1
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();
        em.clear();

        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void bulkDelete() {

        //delete from Member m where m.age > 18
        long count = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();

        em.clear();

        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void sqlFunction() {

        //select function('replace', m.username, 'member', 'M') from Member m
        //application.properties에 등록한 데이터베이스 dialect에 해당 함수가 등록되어 있어야 사용 가능하다.
        List<String> result = queryFactory
                .select(Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2})",
                        member.username, "member", "M"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void sqlFunction2() {

        //select m.username from Member m where m.username = lower(m.username)
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .where(member.username.eq(
//                        Expressions.stringTemplate("function('lower', {0})", member.username)))
                        member.username.lower())) //ANSI 표준에 있는 문법은 어느정도 Querydsl 이 자체적으로 제공해줌
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }
}
