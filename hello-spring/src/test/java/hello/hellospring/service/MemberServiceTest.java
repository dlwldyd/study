package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//단위테스트
class MemberServiceTest {

    MemoryMemberRepository memberRepository=new MemoryMemberRepository();
    MemberService memberService=new MemberService(memberRepository);

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {//테스트코드의 함수 이름은 한글로 적어도 된다.(외국인이랑 일하는게 아닌 이상)
        //given
        Member member=new Member();
        member.setName("spring");
        //when
        memberService.join(member);
        //then
        Member findMember=memberService.findOne(member.getId()).get();
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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}