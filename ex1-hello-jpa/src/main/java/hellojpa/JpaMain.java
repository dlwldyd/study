package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        //하나의 어플리케션에는 팩토리를 하나만 만든다.(db당 하나)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //클라이언트의 요청 하나마다 하나의 매니저를 만든다.
        //보통 한번의 클라이언트 요청에 하나의 트랜잭션이지만 한번의 클라이언트 요청에서 여러번의 트랜잭션이 있을 수는 있다.
        EntityManager em = emf.createEntityManager();

        //jpa 는 데이터를 변경하는 작업을 할 때 트랜잭션 안에서 작업을 해야한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
/* 회원 등록
            //여기에 crud 관련한 코드가 들어감(read 는 매니저 만들고 close 사이, cud 는 tx.begin()하고 매니저 close 사이)
            Member member = new Member();

            //엔티티의 키가 없으면 데이터베이스에 저장할 수 없다.
            member.setId(1L);
            member.setName("HelloA");

            //member 객체 db 에 저장(스프링에서는 위, 아래 코드가 자동화되서 이것만 호출해도 된다.)
            //정확히는 db에 저장하는게 아니라 영속 상태로 만든다.
            em.persist(member);
*/

/* 회원 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());
*/

/* 회원 삭제
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);
*/

/* 회원 수정(em.persist(findMember) 할 필요 없음, 리스트나 맵 같은 컬랙션처럼 생각하면 됨)
            Member findMember = em.find(Member.class, 1L);
            //tx.commit()시점에 스냅샷과 비교하고 변경이 있으면 update 쿼리를 쓰기 지연 SQL 저장소에 쿼리 생성한다.
            //그리고 tx.commit()시점에 쓰기 지연 SQL 저장소에 있는 쿼리를 한꺼번에 db에 전달한다.(flush)
            //스냅샷은 처음 em.find()로 데이터를 조회했을 때의 값이다.
            findMember.setName("helloJPA");
*/

/* JPQL : SQL 은 테이블을 대상으로 쿼리를 하지만 JPQL 은 엔티티 객체를 대상으로 쿼리를 한다.(Member:엔티티, m:별칭(필수))
            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }
*/

/*비영속 상태
            Member member = new Member();
            member.setId(3L);
            member.setName("helloA");
 */

            //member 가 비영속 상태에서 영속 상태로 변함
            //쓰기 지연 SQL 저장소에 쿼리 생성후 저장함(db에 쿼리를 날리는건 아님)
            /*
            <property name="hibernate.jdbc.batch_size" value="10" /> -> 이러한 옵션을
            persistence.xml 에 주면 쓰기 지연 SQL 저장소에 쌓아놓을 수 있는(버퍼링 할 수 있는)
            쿼리의 개수를 정할 수 있음
             */
            //em.persist(member);

            //member 가 영속상태에서 준영속상태로 변함
            /*
            1. 1차 캐시에서 제거된다.
            2. 쓰기 지연 SQL 저장소에 저장되었던 관련 SQL 모두 제거된다.
               따라서 transaction.commit()이 일어나도 데이터베이스에 저장이 되지 않는다.
            */
            //em.clear(); : 영속성 컨텍스트 전체를 준영속 상태로 만듦(영속성 컨텍스트를 완전히 초기화)
            //em.detach(member);

            //쓰기 지연 SQL 저장소에 저장되어 있던 쿼리를 db에 날림(flush) 그리고 데이터베이스 커밋
            tx.commit();
        } catch (Exception e) {

            //롤백
            tx.rollback();
        }finally {

            //데이터베이스 커넥션이 끝날 때 em 을 close
            //영속성 컨텍스트가 관리하던 영속 상태의 엔티티가 모두 준영속 상태가 된다.
            em.close();
        }

        //어플리케이션이 끝날 때 emf 를 close
        emf.close();
    }
}
