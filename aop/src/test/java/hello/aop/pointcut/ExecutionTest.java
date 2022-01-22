package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //execution 은 밑에있는 메서드 정보와 매칭해서 포인트컷 대상을 찾아낸다.
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    //가장 정확한 포인트 컷(특정 메서드, 파라미터 타입 콕찍는 포인트컷)
    @Test
    void exactMatch() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //가장 많이 생략한 포인트컷(생략할 것은 전부 생략하고 모든 메서드에 적용되는 포인트컷)
    @Test
    void allMatch() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* no(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchFalse() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    //String 타입의 파라미터 허용
    //쉼표로 구분해서 특정 타입, 특정 파라미터 개수 지정 가능 -> (String, Integer) : 첫 번째는 String, 두 번째는 Integer
    @Test
    void argsMatching() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //파라미터가 없어야함
    @Test
    void argsMatchingNoArgs() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    //하나의 파라미터만 허용, 모든 타입 허용
    //쉼표로 구분해서 특정 파라미터 개수 지정가능 -> (String, *, Integer, *) : 파라미터 개수 4개 2, 4번째는 타입 무관
    @Test
    void argsMatchingStar() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //파라미터 개수와 무관, 모든 타입 허용
    @Test
    void argsMatchingAll() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //첫 번째 파라미터 타입이 String 이고 파라미터 개수는 무관
    @Test
    void argsMatchingComplex() {
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
