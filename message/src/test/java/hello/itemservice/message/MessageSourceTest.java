package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    //필드 주입, 일반적으로는 필드 주입을 사용하지 않고 테스트 등과 같은 한정적인 상황일 때만 필드 주입을 사용하는 것이 좋다.
    //메세지 테스트를 위해서는 MessageSource 를 주입받아야 함, MessageSource 는 스프링 부트가 알아서 빈으로 등록해줌
    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage(){
        String result = messageSource.getMessage("hello", null, null);
        System.out.println("result = " + result);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() {
        // 해당 이름의 메세지가 없을 때는 예외 터짐
        assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void NotFoundMessageCodeDefaultMessage() {
        // 해당 이름의 메세지가 없어도 defaultMessages 를 넣으면 그게 반환됨
        String result = messageSource.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() {
        //파라미터를 전달할 때는 Object 배열로 전달
        String result = messageSource.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
        //messages_ko.properties 가 없어서 messages.properties 에 있는 hello 메세지 반환
        //만약 Locale 이 en_US 라면 messages_en_US -> messages_en -> messages 순서로 찾음
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang() {
        assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}