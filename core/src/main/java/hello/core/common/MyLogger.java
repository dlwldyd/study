package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//인터페이스면 TARGET_CLASS 대신에 INTERFACE를 쓰면 된다.
//프록시모드를 쓰면 진짜 객체 대신에 가짜 프록시 객체를 주입시켜준다.
//웹스코프가 아니더라도 사용할 수 있다.
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
