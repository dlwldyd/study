package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    //같은 포인트컷을 여러 어드바이스에 적용하기 편함
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //pointcut signature

    //이때는 전체 경로 적어줄 필요 없음
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //joinPoint.getSignature() : 반환타입, 패키지, 클래스이름, 파라미터 정보, 실행된 메서드 정보가 들어있음
        //메서드 정보가 들어있다고 보면 됨
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
