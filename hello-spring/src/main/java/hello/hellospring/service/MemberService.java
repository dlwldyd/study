package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
Component scan을 통해 @Component가 설정되어있으면 스프링이 실행될때 객체를 생성해서 스프링 컨테이너(스프링 빈)에 자동으로 등록한다.
@Service는 @Component의 하위 annotation으로 스프링이 실행될때 객체를 생성해서 스프링 빈에 자동으로 등록한다.
스프링 빈에 등록할때 default로 싱글톤으로 등록된다.
@Component는 HelloSpringApplication(main함수가 있는 클래스)의 하위패키지에 있을때만 자동으로 스프링 빈에 등록된다.
*/
//@Service
public class MemberService {
    private final MemberRepository memberRepository;

    /*@Autowired는 의존관계를 설정한다. 해당클래스가 생성될때 자동으로 생성자가 실행되면서 스프링 빈에 등록되어있는
    memberRepository를 가져온다.(MemberService -> memberRepository인 의존관계를 가진다)
    이러한 것을 DI(Dependency Injection)이라 한다.
     */
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(member1 -> {throw new IllegalStateException("이미 존재하는 회원입니다.");});
        //람다함수에서 중괄호랑 세미콜론 생략은 return문일때만
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
