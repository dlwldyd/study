package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
//        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {

        Member member = new Member("memberV0", 10000);
        repository.save(member);

        Member findMember = repository.findById("memberV0");
        assertThat(findMember).isEqualTo(member);

        repository.update("memberV0", 100);

        Member findMember2 = repository.findById("memberV0");
        assertThat(findMember2.getMoney()).isEqualTo(100);

        repository.delete("memberV0");
        assertThatThrownBy(() -> repository.findById("memberV0")).isInstanceOf(NoSuchElementException.class);
    }
}