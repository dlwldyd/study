package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

//어디에 붙는 애노테이션인지 타겟을 정함
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
//애노테이션이 언제까지 살아남아 있을지 정함 일반적으로는 @Retention(RetentionPolicy.RUNTIME)을 씀
//RetentionPolicy.SOURCE, RetentionPolicy.CLASS(컴파일한후까지, 바이트코드까지)도 있음
@Retention(RetentionPolicy.RUNTIME)
//어노테이션 타입이 수퍼 클래스로부터 상속될 수 있음을 나타낸다. 클래스 A가 @Inherited가 붙은 어노테이션을 받고
//클래스 B가 클래스 A를 상속받을때 해당 어노테이션이 클래스 B에도 적용된다.
@Inherited
//해당 어노테이션을 Javadoc에 포함한다.
@Documented
//@Qualifier("mainDiscountPolicy")를 그대로 붙이면 컴파일시 타입 체크가 안된다. 그래스 다음 같은 어노테이션을 만들어서 문제를 해결할 수 있다.
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {

}
