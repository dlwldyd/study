package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

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