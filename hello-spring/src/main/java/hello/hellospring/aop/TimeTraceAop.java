package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {
    /*
    @Configuration

    public class SpringConfig {

        @Bean
        public TimeTraceAop timeTraceAop() {
            return new TimeTraceAop();
        }
    }

    @Aspect
    public class TimeTraceAop {

        @Around("execution(* hello.hellospring..*(..))")
        public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { }
    }
    TimeTraceAop의 AOP 대상을 지정하는 @Around 코드를 보시면, SpringConfig의 timeTraceAop() 메서드도 AOP로 처리하게 됩니다.
    그런데 이게 바로 자기 자신인 TimeTraceAop를 생성하는 코드인 것이지요. 그래서 순환참조 문제가 발생합니다.
    반면에 컴포넌트 스캔을 사용할 때는 AOP의 대상이 되는 이런 코드 자체가 없기 때문에 문제가 발생하지 않았습니다.
    그러면 AOP 설정 클래스를 빈으로 직접 등록할 때는 어떻게 문제를 해결하면 될까요? 바로 다음과 같이 AOP 대상에서
     SpringConfig를 빼주면 됩니다.
     */
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString()+ " " + timeMs +
                    "ms");
        }
    }
}