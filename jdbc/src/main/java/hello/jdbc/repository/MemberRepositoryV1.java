package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource 사용, JDBCUtils 사용
 */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    public Member save(Member member) throws SQLException {

        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            //데이터베이스 커넥션 얻어옴
            conn = getConnection();
            //문자열로 작성된 sql 을 쿼리로 날릴 수 있도록 변환함
            pstmt = conn.prepareStatement(sql);
            //파라미터 바인딩
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            //쿼리 날림
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            //데이터베이스 사용 후 리소스 반환 필수, 반환 안하면 리소스 누수 발생
            close(conn, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //데이터베이스 커넥션 얻어옴
            conn = getConnection();
            //문자열로 작성된 sql 을 쿼리로 날릴 수 있도록 변환함
            pstmt = conn.prepareStatement(sql);
            //파라미터 바인딩
            pstmt.setString(1, memberId);
            //select 쿼리 실행
            rs = pstmt.executeQuery();
            //ResultSet 은 select 쿼리 결과
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }
            throw new NoSuchElementException("member not found, memberId = " + memberId);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            //데이터베이스 사용 후 리소스 반환 필수, 반환 안하면 리소스 누수 발생
            close(conn, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            //데이터베이스 커넥션 얻어옴
            conn = getConnection();
            //문자열로 작성된 sql 을 쿼리로 날릴 수 있도록 변환함
            pstmt = conn.prepareStatement(sql);
            //파라미터 바인딩
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            //데이터베이스 사용 후 리소스 반환 필수, 반환 안하면 리소스 누수 발생
            close(conn, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            //데이터베이스 커넥션 얻어옴
            conn = getConnection();
            //문자열로 작성된 sql 을 쿼리로 날릴 수 있도록 변환함
            pstmt = conn.prepareStatement(sql);
            //파라미터 바인딩
            pstmt.setString(1, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            //데이터베이스 사용 후 리소스 반환 필수, 반환 안하면 리소스 누수 발생
            close(conn, pstmt, null);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(conn);
    }

    private Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.info("get connection={}, class={}", connection, connection.getClass());
        return connection;
    }
}
