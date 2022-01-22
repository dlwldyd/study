package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //statefulService는 service에 관한 객체가 아니라 쓰레드라 가정
        //statefulService1 : Thread1, statefulService2 : Thread2
        //Thread1 : 사용자1이 10000원 주문
        statefulService1.order("user1", 10000);
        //Thread2 : 사용자2가 20000원 주문
        statefulService2.order("user2", 20000);

        int price = statefulService1.getPrice();
        Assertions.assertThat(price).isEqualTo(20000);
    }
    static class TestConfig{
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}