package springdata.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springdata.datajpa.dto.MemberDto;
import springdata.datajpa.entity.Member;
import springdata.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

//    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    //파라미터로 Pageable 을 받으면 url 파라미터를 알아서 바인딩 해줌
    //default 로 20개씩 나옴(localhost:8080/members 라고 보내면 localhost:8080/members?page=0 과 같이 나옴
    //application.properties 에 spring.data.web.pageable.default-age-size 로 글로벌로 페이지 사이즈 설정 가능
    //page 지정해서 원하는 페이지 받을 수 있음
    //@PageableDefault 어노테이션을 통해 해당 핸들러메서드에 대해서만 설정 변경 가능
    //마지막에 페이지 정보가 붙음
    //size 로 개수 지정 가능(localhost:8080/members?page=0&size=3)
    //sort 로 sorting 조건 넣을 수 있음(localhost:8080/members?sort=username,desc&sort=age,desc)
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 10, sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member ->
                new MemberDto(member.getId(), member.getUsername(), null));
        return map;
    }
}
