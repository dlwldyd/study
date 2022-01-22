package hello.core.singleton;

public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }

    //생성자를 private로 만들어서 새로운 객체 생성을 막는다.
    private SingletonService(){}
}
