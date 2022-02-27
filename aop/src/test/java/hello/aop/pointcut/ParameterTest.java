package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {
        }

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            //getArgs()함수를 통해 배열의 형태로 조인포인트의 파라미터에 접근할 수 있다.
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArg1]{} arg1={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        //args()에 파라미터 타입 대신 파라미터 이름을 넣고 어드바이스에 같은 이름의 파라미터를 받으면 조인포인트의 파라미터에 접근할 수 있다.
        //단, 어드바이스의 파라미터 타입이 조인포인트의 파라미터를 받을 수 없는 타입이면 어드바이스는 실행되지 않는다.(조인포인트로 선정X)
        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArg2]{} arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        //hello(String)의 파라미터가 String 타입이기 때문에 String 으로 받을 수 있다.
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        //obj 에 프록시가 들어옴(스프링 빈으로 등록된 객체가 들어옴)
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        //obj 에 프록시가 실제로 호출하는 대상(target)이 들어옴
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        /*
        어노테이션 정보를 매개변수로 받기 위해서는 @target 에 파라미터 이름을 넣는 대신에 어드바이스를 적용할 어노테이션 타입을
        어드바이스의 파라미터에 명시해야한다. 예를 들면 @ClassAop 가 붙은 모든 클래스의 메서드를 조인포인트로 선정하고 싶을 때
        @target(annotation)만으로는 어떤 어노테이션이 붙은 클래스를 조인포인트로 선정할지 알 수 없다. 따라서
        아래의 atTarget(JoinPoint joinPoint, ClassAop annotation)처럼 파라미터 타입을 어노테이션 타입으로 해서
        @ClassAop 이 붙은 클래스를 조인포인트로 선정할 것을 명시해야 한다.
         */
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{}, annotation={}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{}, annotation={}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation)")
        //@target 과 마찬가지
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
        }
    }
}
