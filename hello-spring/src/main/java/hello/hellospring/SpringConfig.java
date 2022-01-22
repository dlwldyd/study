package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration//설정을 위한 클래스임을 나타낸다.
public class SpringConfig {

    /*
    스프링데이터 JPA에 의해 SpringDataMemberRepository는 자동으로 생성되어 있다.
    SpringDataMemberRepository는 MemberRepository를 상속받고 있기 때문에 MemberRepository로 참조할 수 있다.
     */
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //JPA이용
    /*private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }*/

    //jdbc, jdbc template 이용
    /*private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    //스프링데이터 JPA를 쓰면 memberRepository가 스프링빈에 자동으로 등록되기 때문에 필요없다.
    /*@Bean//스프링이 시작되면 아래의 함수를 실행해서 return된 객체를 스프링 빈에 등록한다.
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcMemberTemplateRepository(dataSource);
        return new JpaMemberRepository(em);
    }*/

    @Bean
    public MemberService memberService(){
        //MemberService안의 매개변수로 사용된 memberRepository()는 위에있는 함수이다.
        //return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }

    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }
}
