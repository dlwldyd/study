package springdata.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import springdata.datajpa.dto.MemberDto;
import springdata.datajpa.entity.Member;
import springdata.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member1", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("member1", 15);

        assertThat(result.get(0)).isEqualTo(member2);
    }

    @Test
    public void findHelloTop3By() {
        memberRepository.findTop3UsernameBy();
    }

    @Test
    public void namedQuery() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAAA("member2");

        assertThat(result.get(0)).isEqualTo(member2);
    }

    @Test
    public void testQuery() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findMember("member2", 20);

        assertThat(result.get(0)).isEqualTo(member2);
    }

    @Test
    public void findUsernameList() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> usernameList = memberRepository.findUsernameList();

        assertThat(usernameList).contains("member1", "member2");
    }

    @Test
    public void findMemberDto() {

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        member1.setTeam(teamA);
        member2.setTeam(teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        System.out.println("memberDto = " + memberDto);
    }

    @Test
    public void findByNames() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMembers = memberRepository.findByNames(Arrays.asList("member1", "member2"));
        assertThat(findMembers).contains(member1, member2);
    }

    @Test
    public void returnType() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        Member member3 = new Member("member1", 15);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> memberList = memberRepository.findListByUsername("member1");
        Member singleMember = memberRepository.findMemberByUsername("member2");
        Member nullMember = memberRepository.findMemberByUsername("none");
        Optional<Member> optionalMember = memberRepository.findOptionalByUsername("member2");

        assertThat(memberList).contains(member1, member3);
        assertThat(singleMember).isEqualTo(member2);
        assertThat(optionalMember.get()).isEqualTo(member2);
        assertThat(nullMember).isNull();
        assertThatThrownBy(() -> memberRepository.findMemberByUsername("member1"))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);
        assertThatThrownBy(() -> memberRepository.findOptionalByUsername("member1"))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }

    @Test
    public void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        //PageRequest 는 Pageable 의 구현체, 정렬 가능
        //(offset, limit, 정렬과 관련) ->  파라미터를 이순으로 넣는다.
        //정렬 조건이 복잡해지면 그냥 @Query 로 직접 짜면 된다.
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);
        /*
        Slice 는 전체 row 수가 몇개인지, 현재 내가 몇 페이지에 있는지 모르기 때문에 limit 를 PageRequest 에 주어진대로
        가져오면 더 이상 데이터가 없어도 쿼리를 보내야지 알 수 있다. 따라서 Slice 는 PageRequest 에 주어진 limit 보다
        1 크게 데이터를 가져와서 쿼리를 보내지 않아도 다음 페이지가 있는지 알 수 있게 한다.
         */
        Slice<Member> slice = memberRepository.findSliceByAge(age, pageRequest);
        List<Member> listByAge = memberRepository.findListByAge(age, pageRequest);

        //map 메서드로 DTO 로 변환도 쉽게 가능하다.
        //스트림으로 바꿔서 하면 Page<MemberDto> 로 변환이 어려움
        //이거를 JSON 으로 반환하면 DTO 의 필드 말고도 totalPages, totalElement 등이 전부 JSON 으로 변환됨
        Page<MemberDto> map = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        //페이징해서 쿼리한 결과를 반환함
        List<Member> content = page.getContent();
        //전체 데이터의 row 수를 반환함, Slice 에는 없음
        long totalElements = page.getTotalElements();
        //현재 페이지의 번호를 반환함(0부터 시작)
        int number = page.getNumber();
        //전체 페이지 개수 반환함, Slice 에는 없음
        int totalPages = page.getTotalPages();
        //현재 페이지가 첫번째 페이지인지 반환함
        boolean first = page.isFirst();
        //다음 페이지가 존재하는지 반환함
        boolean hasNext = page.hasNext();

        assertThat(content.size()).isEqualTo(3);
        assertThat(totalElements).isEqualTo(5);
        assertThat(number).isEqualTo(0);
        assertThat(totalPages).isEqualTo(2);
        assertThat(first).isTrue();
        assertThat(hasNext).isTrue();

        List<Member> content1 = slice.getContent();
//        long totalElements = slice.getTotalElements();
        int number1 = slice.getNumber();
//        int totalPages = slice.getTotalPages();
        boolean first1 = slice.isFirst();
        boolean hasNext1 = slice.hasNext();

        assertThat(content1.size()).isEqualTo(3);
        assertThat(number1).isEqualTo(0);
        assertThat(first1).isTrue();
        assertThat(hasNext1).isTrue();

        // 사용 불가
//        List<Member> content1 = listByAge.getContent();
//        long totalElements = listByAge.getTotalElements();
//        int number1 = listByAge.getNumber();
//        int totalPages = listByAge.getTotalPages();
//        boolean first1 = listByAge.isFirst();
//        boolean hasNext1 = listByAge.hasNext();

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);
    }

    @Test
    void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        int resultCount = memberRepository.bulkAgePlus(20);
//        em.clear();

        Member member5 = memberRepository.findByUsernameAAA("member5").get(0);
        System.out.println("member5 = " + member5);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void findMembers() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint() {
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
        assertThat(findMember.getUsername()).isEqualTo("member2");
        em.flush();
        em.clear();
        assertThat(memberRepository.findById(findMember.getId()).get().getUsername()).isEqualTo("member1");
    }

    @Test
    @Rollback(value = false)
    public void lock() {

        Team team = new Team("teamA");
        teamRepository.save(team);
        Member member1 = new Member("member1", 10, team);
        memberRepository.save(member1);
        team.setName("teamB");
        em.flush();
        em.clear();

        Team findTeam = teamRepository.findLockByName("teamB").get(0);
        System.out.println(" ============================================== ");
        System.out.println("findTeam = " + findTeam);
        System.out.println(" ============================================== ");

        Member member2 = new Member("member2", 10, team);
        memberRepository.save(member2);
        em.flush();
        em.clear();
        Team findTeam2 = teamRepository.findLockByName("teamB").get(0);
        System.out.println(" ============================================== ");
        System.out.println("findTeam = " + findTeam2);
        System.out.println(" ============================================== ");
    }

    @Test
    public void callCustom() {
        List<Member> memberCustom = memberRepository.findMemberCustom();
    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        Member member1 = new Member("member1");
        memberRepository.save(member1); //prePersist 호출

        Thread.sleep(100);
        member1.setUsername("member2");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member1.getId()).get();

        System.out.println("findMember.getCreatedDate() = " + findMember.getCreatedDate());
        System.out.println("findMember.getUpdatedDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreatedBy() = " + findMember.getCreatedBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }

    @Test
    @Rollback(value = false)
    void mergeTest() {
        Team team = new Team("teamA");
        em.persist(team);

        Member member = new Member("member1", 20, team);
        em.persist(member);
        em.flush();
        em.detach(member);

        Member new_member = new Member("new member", 25, team);
        member.setUsername("member2");
        System.out.println(" =========================================================== ");
        em.merge(member);
        em.merge(new_member);
    }

    @Test
    void nativeQuery() {
        Team team = new Team("teamA");
        em.persist(team);

        Member member = new Member("member1", 20, team);
        Member new_member = new Member("new member", 25, team);
        em.persist(member);
        em.persist(new_member);

        em.flush();
        em.clear();

        Member result = memberRepository.findUsingNativeQueryByUsername("member1");
        System.out.println("result = " + result);
    }
}