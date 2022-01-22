package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{

        //Member는 스프링 컨테이너에 의해 관리되는 객체가 아님
        //noBean1이 스프링빈으로 등록이 안되어있기 때문에 setNoBean1이 호출이 되지 않는다.
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        }

        //@Nullable로 되어있기 때문에 noBean2가 스프링빈으로 등록이 안되어있지만 setNoBean2는 호출되고 null로 출력된다.
        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        }

        //Optional<Member>로 되어있기 때문에 noBean3가 setNoBean3는 호출되지만 Optional.empty로 출력된다.
        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }

    }
}
