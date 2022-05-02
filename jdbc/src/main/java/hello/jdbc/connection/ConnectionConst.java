package hello.jdbc.connection;

public abstract class ConnectionConst {
    //url 값에 따라 어떤 데이터베이스 드라이버가 사용될지 결정됨(외부 라이브러리로 포함된 드라이버 중에서)
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
