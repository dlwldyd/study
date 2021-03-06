package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    /*
    하나의 애스펙트에 여러 어드바이스가 있을 수 있지만 그렇게 되면 어드바이스 적용 순서를 보장하지 않음
    어드바이스 적용 순서를 보장하고 싶으면 @Order 를 통해 순서를 정해야 하는데 @Order 는 클래스 레벨에만 붙을 수 있음
    따라서 순서를 정하고 싶으면 어드바이스를 별도의 애스펙트로 분리해서 @Order 를 적용해야함, 굳이 static class 로 분리할
    필요는 없음
     */
    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            //joinPoint.getSignature() : 반환타입, 패키지, 클래스이름, 파라미터 정보, 실행된 메서드 정보가 들어있음
            //메서드 정보가 들어있다고 보면 됨
            log.info("[log] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect {
        @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
            try {
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
                throw e;
            }finally {
                log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
            }
        }
    }
}
