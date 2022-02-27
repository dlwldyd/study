package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//application.properties 이 테스트에만 설정, JDK 동적 프록시로도 생성됨, true 면 항상 CGLIB 로 생성
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    static class ThisTargetAspect {

        //프록시를 대상으로 포인트컷 판별(프록시 대상이라기 보다는 현재 스프링빈으로 등록된 객체(this)를 대상)
        //프록시 생성시에는 프록시가 없기 때문에 target 을 대상으로 포인트컷 판별 후 프록시를 생성하지만
        //프록시 생성 후에는 스프링빈으로 프록시가 등록되어 있기 때문에 프록시를 대상으로 포인트컷 판별
        @Around("this(hello.aop.member.MemberService)")
        //프록시 객체는 MemberService 인터페이스를 구현하기 때문에(JDK 동적 프록시) 이 어드바이스는 적용된다.
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //실제 기능을 하는 객체(target)을 대상으로 포인트컷 판별
        //타겟 객체는 MemberServiceImpl 객체이고 이 객체는 MemberService 를 구현하고 있기 때문에 이 어드바이스는 적용됨
        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(hello.aop.member.MemberServiceImpl)")
        /*
        spring.aop.proxy-target-class=false 설정으로 인해 프록시는 MemberService 를 구현한다. 따라서
        해당프록시는 MemberServiceImpl 의 자식 타입이 아니기 때문에 이 어드바이스는 적용되지 않는다.
         */
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        //타겟 객체는 MemberServiceImpl 객체이기 때문에 이 어드바이스는 적용됨
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
