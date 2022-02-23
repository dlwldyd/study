package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
/*
프록시모드를 쓰면 진짜 객체 대신에 가짜 프록시 객체를 주입시켜준다.
가짜 프록시 객체를 주입하는 이유는 웹 스코프의 경우에는 클라이언트의 요청이 오던 세션이 생성되던지 해야지 객체가 생성된다.
그렇기 때문에 프록시 모드를 쓰지 않고 웹 스코프가 설정된 객체를 다른 객체에서 주입받으려 하면 처음 초기화되는 시점에는
클라이언트의 요청도 없고 세션이 생성되지도 않은 시점이기 때문에 객체가 없어서 주입을 받을 수 없다.
그렇기 때문에 프록시 모드를 사용해 프록시 객체를 주입해 둔다.
 */
//인터페이스면 TARGET_CLASS 대신에 INTERFACE를 쓰면 된다.
//웹스코프가 아니더라도 사용할 수 있다.

/*
웹스코프 종류
request : HTTP 요청마다 별도의 빈 인스턴스가 생성되고, 관리된다.
session : HTTP session마다 별도의 빈 인스턴스가 생성되고, 관리된다.
application : ServletContext와 생명주기를 같이한다.(스프링에서는 DispatcherServlet)(모든 클라이언트가 값을 공유함)
websocket : 웹 소켓과 같은 생명주기를 가진다.
 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestUrl;

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestUrl + "]" + message);
    }

    @PostConstruct
    public void init(){
        //전세계에서 하나뿐인 uuid가 생성된다.
        this.uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean created : " + this);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("[" + uuid + "] request scope bean closed : " + this);
    }
}
