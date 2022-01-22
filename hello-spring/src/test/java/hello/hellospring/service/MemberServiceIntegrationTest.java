package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
/*
스프링 컨테이너와 테스트를 함께 실행한다. 이게 없으면 스프링 컨테이너에 있는 객체를 못들고온다.(데이터베이스 연결 등) 통합테스트를
할 때 이 어노테이션을 사용한다.
 */
@Transactional
/*
테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지않는다
 */
class MemberServiceIntegrationTest {

    MemberRepository memberRepository;
    MemberService memberService;

    @Autowired
    public MemberServiceIntegrationTest(MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @Test
    void 회원가입() {//테스트코드의 함수 이름은 한글로 적어도 된다.(외국인이랑 일하는게 아닌 이상)
        //given
        Member member=new Member();
        member.setName("spring");
        //when
        Long saveId=memberService.join(member);
        //then
        Member findMember=memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 회원_중복_예외(){
        //given
        Member member1=new Member();
        Member member2=new Member();
        member1.setName("spring");
        member2.setName(("spring"));
        //when
        memberService.join(member1);


        /*try{
            memberService.join(member1);
            memberService.join(member2);
            fail();//이거 실행되면 실패라고 뜸 즉, 위의 코드에서 catch문으로 안넘어가면 실패
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then
        IllegalStateException e=assertThrows(IllegalStateException.class, ()->memberService.join(member2));
        //2번째 매개변수에 있는 람다식이 실행될때 1번째 매개변수에 있는 예외가 throw 되지 않으면 실패로 뜸
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}