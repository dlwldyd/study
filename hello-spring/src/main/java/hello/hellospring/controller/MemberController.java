package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/*
Component scan을 통해 @Component가 설정되어있으면 스프링이 실행될때 객체를 생성해서 스프링 컨테이너(스프링 빈)에 자동으로 등록한다.
@Controller는 @Component의 하위 annotation으로 스프링이 실행될때 객체를 생성해서 스프링 빈에 자동으로 등록한다.
스프링 빈에 등록할때 default로 싱글톤으로 등록된다.
@Component는 HelloSpringApplication(main함수가 있는 클래스)의 하위패키지에 있을때만 자동으로 스프링 빈에 등록된다.
*/
@Controller
public class MemberController {
    MemberService memberService;

    /*@Autowired는 의존관계를 설정한다. 해당클래스가 생성될때 자동으로 생성자가 실행되면서 스프링 빈에 등록되어있는 memberService를
    가져온다.(MemberController -> memberService인 의존관계를 가진다)
    이러한 것을 DI(Dependency Injection)이라 한다.
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "/members/createMemberForm";
    }

    /*
    post는 PostMapping이 되어있는 함수를 실행한다. post로 주어진 데이터는 실행되는 함수의 매개변수로 주어진 객체에 들어간다.
    주어진 데이터는 html에 정의된 name과 같은 이름의 매개변수에 setter함수를 통해 들어가게 된다.
     */
    @PostMapping("/members/new")
    public String create(MemberForm memberForm){
        Member member=new Member();
        member.setName(memberForm.getName());
        memberService.join(member);
        return "redirect:/";
    }
    /*public String create(Member member){
        memberService.join(member);
        System.out.println(member.getName());
        return "redirect:/";
    }*/

    @GetMapping("/members")
    public String list(Model model){
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("members", memberList);
        return "/members/memberList";
    }
}
