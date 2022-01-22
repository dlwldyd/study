package hello.core.member;

import hello.core.annotation.MainDiscountPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    /*
    AppConfig로 스프링 컨테이너에 등록하면 memberService()함수가 실행되면서 의존관계가 주입되겠지만
    컴포넌트 스캔방식을 사용하면 AutoAppConfig에는 의존관계에 관한 어떠한 함수도 없기 때문에 의존관계가 주입되지 않는다.
    따라서 컴포넌트 스캔 방식으로 의존관계를 주입하기 위해서는 @Autowired를 의존관계 주입을 위한 생성자 위나
    필드에 붙여주어 의존관계를 주입해 주어야 한다. 단, 생성자가 하나뿐이면 붙여주지 않아도 된다.
    생성자나 필드에 주입될 객체를 스프링 컨테이너에서 찾을 때 타입을 이용해서 찾는다.(같은타입의 스프링 빈을 넣음)
    같은 타입이 여러개 있을 때는 타입이 아니라 빈 이름으로 조회한다. 따라서 같은 타입이 여러개 있을 때는 생성자의 매개변수
    이름을 빈 이름과 똑같이 설정해주어야 한다.
     */
    @Autowired
    //@Qualifier("subMemberRepository")는 subMemberRepository라는 이름의 식별자를 가진 빈을 찾아준다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
