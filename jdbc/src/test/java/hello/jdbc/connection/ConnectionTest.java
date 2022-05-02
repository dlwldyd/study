package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        //항상 새로운 DB 커넥션 획득
        //기존 DriverManager 는 DataSource 의 구현체가 아니라 DataSource 를 사용해서 커넥션 획득 불가
        Connection conn1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection conn2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection={}, class={}", conn1, conn1.getClass());
        log.info("connection={}, class={}", conn2, conn2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        //항상 새로운 DB 커넥션 획득, DataSource 인터페이스를 통해 커넥션 획득
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        //DB 커넥션 풀로부터 커넥션을 얻어옴(커넥션 풀링)
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection conn1 = dataSource.getConnection();
        log.info("connection={}, class={}", conn1, conn1.getClass());
        conn1.close();

        Connection conn2 = dataSource.getConnection();
        log.info("connection={}, class={}", conn2, conn2.getClass());
    }
}
