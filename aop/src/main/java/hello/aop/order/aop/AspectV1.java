package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
//애스펙트, aop로 사용하려면 스프링 빈으로 등록해야한다.
@Aspect
public class AspectV1 {

    //포인트컷
    @Around("execution(* hello.aop.order..*(..))")
    //어드바이스
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //joinPoint.getSignature() : 반환타입, 패키지, 클래스이름, 파라미터 정보, 실행된 메서드 정보가 들어있음
        //조인포인트 정보가 들어있다고 보면 됨
        log.info("[log] {}", joinPoint.getSignature());
        //조인포인트 실행(필수)
        return joinPoint.proceed();
    }
}
