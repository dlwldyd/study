package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    /*
    가장 강력한 어드바이스로 @Before, @AfterReturning, @AfterThrowing, @After의 모든 기능을 다 할 수 있고,
    반환값을 바꿔치기할 수도 있고 예외를 바꿔치기 할 수도 있다. 파라미터로 ProceedingJoinPoint 타입을 받아야함
    joinPoint.proceed() 필수
     */
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {

            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            //@AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {

            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        }finally {

            //@After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    //타겟이 호출되기 전에 호출된다. 타겟 호출 이후 로직을 넣을 수는 없음
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) { //ProceedingJoinPoint 사용 불가
        log.info("[before] {}", joinPoint.getSignature());
        //자동으로 타겟 실행
    }

    //타겟 호출 후 값이 리턴되었을 때 호출된다.
    //리턴된 값을 조작할 수는 있지만, 프록시로 교체하는 등 리턴값을 다른 값으로 바꿀 수는 없음
    //ProceedingJoinPoint 사용 불가, returning 속성 값은 타겟이 호출됐을때 리턴된 값이 저장되는 파라미터의 이름
    //returning 으로 설정한 값이 타겟을 호출했을 때 리턴되는 타입을 받을 수 없다면 null 값을 받는게 아니라 어드바이스 자체가 호출안됨
    //returning 으로 설정한 값(파라미터로 있음)을 Object 로 설정하면 무조건 값을 받을 수 있음
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) { //ProceedingJoinPoint 사용 불가, returning 속성 값으로 설정된 이름과 이름을 맞춰야함
        log.info("[return] {} result={}", joinPoint.getSignature(), result);
    }

    //예외를 조작할 수는 있지만 예외를 다른 예외로 바꿔치기할 수는 없다.
    //ProceedingJoinPoint 사용 불가
    //throwing 속성 값은 throw 된 예외가 저장될 파라미터의 이름
    //throwing 으로 설정한 값이 throw 된 예외를 받을 수 없다면(타입 미스매치) null 값을 받는게 아니라 어드바이스 자체가 호출안됨
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) { //ProceedingJoinPoint 사용 불가, throwing 속성 값으로 설정된 이름과 이름을 맞춰야함
        log.info("[ex] {} message={}", ex, ex.getMessage());
        //Throw e 가 자동으로 됨
    }

    //ProceedingJoinPoint 사용 불가
    //예외가 터지든 안터지든 무조건 실행됨
    @After("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) { //ProceedingJoinPoint 사용 불가
        log.info("[after] {}", joinPoint.getSignature());
    }
}
