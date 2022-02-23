package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//@Component가 붙은 클래스를 스프링빈으로 등록하려면 아래의 어노테이션을 지정해줘야한다.
@ComponentScan(
        //basePackages = "hello.core.member",
        /*
        컴포넌트 스캔으로 탐색할 패키지의 시작 위치를 지정한다. 위의 경우에는 member 패키지 아래에 있는 클래스 파일만
        컴포넌트 스캔의 대상이 된다. 지정하지 않으면 @ComponentScan을 지정한 클래스의 패키지의 하위 패키지를 다 뒤진다.
        지금같은 경우에는 hello.core 이하를 전부 뒤진다.
         */
        //basePackages = {"hello.core.member", "hello.core.discount"} 여러개도 지정 가능하다.

        /*
        스프링부트가 자동으로 생성해주는 CoreApplication에 가보면 @SpringBootApplication이 붙어있는데
        @SpringBootApplication의 상위에 ComponentScan이 붙어있어 자동으로 CoreApplication의 패키지인 hello.core 아래를
        다 뒤지기 때문에 굳이 @Component나 @Service, @Controller 등을 사용하기 위해 @ComponentScan을 만들 필요는 없다.
         */

        /*
        아래의 설정을 사용하면 특정 어노테이션은 컴포넌트 스캔 대상에서 제외할 수 있다. 아래의 경우에는
        @Configuration 어노테이션이 붙은 클래스는 자동으로 스프링빈에 등록되지 않는다.(원래는 등록됨)
        includeFilters 를 통해 컴포넌트 스캔 대상 어노테이션을 추가로 지정할 수도 있다.
         */
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
