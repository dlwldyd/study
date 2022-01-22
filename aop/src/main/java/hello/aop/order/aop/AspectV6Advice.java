package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

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

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) { //ProceedingJoinPoint 사용 불가
        log.info("[before] {}", joinPoint.getSignature());
        //자동으로 타겟 실행
    }

    //리턴될 값을 조작할 수는 있지만, 프록시로 교체하는 등 리턴값을 다른 값으로 바꿀 수는 없음
    //ProceedingJoinPoint 사용 불가, returning 값은 타겟이 호출됬을때 리턴된 값이 저장되는 파라미터의 이름
    //returning 으로 설정한 값이 타겟을 호출했을 때 리턴되는 타입을 받을 수 없다면 null 값을 받는게 아니라 어드바이스 자체가 호출안됨
    //returning 으로 설정한 값(파라미터로 있음)을 Object 로 설정하면 무조건 값을 받을 수 있음
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} result={}", joinPoint.getSignature(), result);
    }

    //예외를 조작할 수는 있지만 예외를 다른 예외로 바꿔치기할 수는 없다.
    //ProceedingJoinPoint 사용 불가
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", ex, ex.getMessage());
        //Throw e 가 자동으로 됨
    }

    //ProceedingJoinPoint 사용 불가
    @After("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
