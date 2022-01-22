package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//빈이 초기화(의존관계 주입)될 때 호출하고 싶으면 InitializingBean, 소멸할 때 호출하고 싶으면 DisposableBean를 상속한다. 잘  사용X
public class NetworkClient{

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect(){
        System.out.println("connect : " + url);
    }

    public void call(String msg){
        System.out.println( "call : " + url + " message = " + msg);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close : " + url);
    }

    //의존관계 주입이 끝나면. 호출됨 주로 이걸 사용. 스프링이 아니라 자바가 지원하기 때문에 스프링컨테이너가 아닌 다른 컨테이너라도 적용됨
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    //빈이 소멸될때 호출됨. 주로 이걸 사용. 스프링이 아니라 자바가 지원하기 때문에 스프링컨테이너가 아닌 다른 컨테이너라도 적용됨
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
