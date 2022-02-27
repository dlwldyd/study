package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //같은 포인트컷을 여러 어드바이스에 적용하기 편함
    //포인트컷을 모듈화해 한곳에 모아놓고 사용할 수 있다. 이때 포인트컷은 public 으로 만들어야한다.

    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {
    } //pointcut signature

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {
    }
}
