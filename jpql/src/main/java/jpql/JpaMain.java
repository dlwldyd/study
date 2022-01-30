package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            //TypedQuery 를 쓸 수 있는 경우 -> 반환 타입이 명확한 경우만 사용할 수 있음
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

            //TypedQuery 를 쓸 수 없는 경우
            Query query3 = em.createQuery("select m.username, m.age from Member m");

            //컬렉션 받아오기, 결과가 없으면 빈 리스트 반환
            //이렇게 받아온 모든 엔티티는 영속성 컨텍스트에 관리된다.(엔티티를 받아왔을 때만)
            List<Member> resultList = query1.getResultList();

            //값 하나만 받아오기, 결과가 없으면 NoResultException, 결과가 하나가 아니면 NonUniqueResultException 반환
            //Spring Data JPA 의 추상화된 함수 사용하면 결과가 없을 때 비어있는 Optional 로 반환해줌
            Member result = query1.getSingleResult();

            //파라미터 바인딩, ':변수명'을 사용하면 파라미터를 바인딩 할 수 있다.
            TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.username = :username", Member.class);
            // :username member1으로 바인딩 함
            query4.setParameter("username", "member2");

            /*
            엔티티에 속해 있는 엔티티를 받아올 때는 jpql 을 조인을 이용해서 값을 안 가져와도(select m.team from Member m)
            자동으로 조인해서 값을 가져오지만 조인은 쿼리를 튜닝할 여지가 많기 때문에 최대한 native sql 처럼 사용하는 것이 좋다.
             */
            TypedQuery<Team> query = em.createQuery("select t from Member m join m.team t", Team.class);

            /*
            스칼라 타입을 받아올 때는 TypeQuery 대신 Query 써도 되지만 그렇게 하면 Object[]를 받아온다.
            그래서 타입캐스팅을 해야하고 여러모로 불편한게 많다. Query 를 쓰는 것 보단 DTO 를 만들어 해당 객체에 값을
            넣는게 좋다. 하지만 jpql 에서는 엔티티에만 값을 넣을 수 있는데 엔티티가 아닌 객체에 값을 넣으려면
            select 문에 new 명령어를 써야한다. 이때 DTO 가 들어있는 패키지 경로까지 모두 써야한다.
            또한 값을 넣기 위해 생성자도 필요하다.
             */
            // 내부 조인 : select m from Member m join m.team t on m.username = t.name(연관이 없는 값끼리 비교할 때 on 절을 사용하면 비교할 수 있다)
            // 외부 조인 : select m from Member m left join m.team t on t.name = 'teamA'
            // 세타 조인 : select count(m) from Member m, Team t where m.username = t.name(연관관계가 없는 엔티티끼리 비교할 때 사용)
            // 연관관계가 없는 내부 조인 : select m from Member m join Team t on m.username = t.name(세타조인처럼 작동한다)
            // 연관관계가 없는 외부 조인 : select m from Member m left join Team t on m.username = t.name(연관관계가 없는 엔티티 끼리의 외부조인)
            // 타입 표현 : select m.username, 'hello', true from Member m where m.type = jpql.MemberType.ADMIN(enum 타입은 패키지 경로까지 적어줘야함 파라미터 바인딩을 사용하면 패키지 경로 안적어도 됨)
            List<MemberDTO> resultList1 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            //페이징
            //setFirstResult : offset, setMaxResults : row 개수
            List<Member> resultList2 = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            // 조건식(case 식)
            String queryString =
                                "select " +
                                    "case when m.age <= 10 then '학생요금' " +
                                    "when m.age >=60 then '경로요금' " +
                                    "else '일반요금' " +
                                    "end " +
                                "from Member m";
            List<String> resultList3 = em.createQuery(queryString, String.class)
                    .getResultList();

            // coalesce : 값이 있으면 값을 가져오고 null 이면 2번째 파라미터로 넣은 값이 반환
            List<String> resultList4 = em.createQuery("select coalesce(m.username, '이름 없는 회원') from Member m", String.class)
                    .getResultList();

            // nullif : m.username 이 관리자면 해당 값 대신 null 이 반환됨
            List<String> resultList5 = em.createQuery("select nullif(m.username, '관리자') from Member m", String.class)
                    .getResultList();

            /*==========================JPQL 기본 함수===============================
            concat : select concat(m.username, m.username) from Member m -> 문자열 합침
            substring : select substring(m.username, 2, 3) from Member m -> 문자열 잘라서 반환
            locate : select locate('ber', m.username) from Member m -> 인덱스 반환
            lower, upper : select upper(m.username) from Member m -> 대소문자로 변환해서 반환
            abs, sqrt, mod : 제곱, 제곱근, 나머지 반환
            length : 데이터 길이 반환
            size : select size(t.members) from Team t -> 컬렉션의 크기를 반환
             */

            /*==========================사용자 정의 함수==============================
            내가 사용하는 데이터베이스의 dialect 를 상속하는 클래스를 만든 후 persistence.xml 이나 application.properties
            에 기존의 dialect 대신 내가 만든 클래스를 dialect 로 등록하면 사용자 정의 함수를 사용할 수 있다.
            select function('group_concat', m.username) from Member m
             */


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
