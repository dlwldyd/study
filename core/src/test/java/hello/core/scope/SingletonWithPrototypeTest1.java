package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCnt()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        //새로 생성된 프로토타입 빈이기 때문에 2가 아닌 1이 된다.
        assertThat(prototypeBean2.getCnt()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int cnt1 = clientBean1.logic();
        assertThat(cnt1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int cnt2 = clientBean2.logic();
        assertThat(cnt2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean{

        /*
        프로토타입 빈을 그대로 주입받을 경우 싱글톤 빈 생성시점에 주입 받은 프로토타입 빈 객체를 계속해서 사용하기 때문에
        prototypeBean을 참조할 때 마다 새로운 프로토타입 빈을 생성해 사용할 수 없다. 따라서 싱글톤 빈 내에서 프로토타입 빈을
        주입받고 해당 프로토타입 빈을 참조할 때마다 새로운 프로토타입 빈을 생성해 사용하고 싶으면 ObjectProvider<PrototypeBean>
        또는 Provider<PrototypeBean>를 사용해야한다.
         */
        //private final PrototypeBean prototypeBean;

        //스프링에 의존적, 편의기능 더 있음
        //private final ObjectProvider<PrototypeBean> prototypeBeanProvider;

        //자바 표준이지만 build.gradle에 라이브러리 추가해야함. javax.inject.Provider 사용
        private final Provider<PrototypeBean> prototypeBeanProvider;

        /*
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }
        */

        /*
        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }
        */

        public ClientBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic(){
            //이 함수를 실행할 때마다 PrototypeBean을 생성한다.
            //PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCnt();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int cnt = 0;

        public void addCount() {
            this.cnt++;
        }

        public int getCnt() {
            return cnt;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
