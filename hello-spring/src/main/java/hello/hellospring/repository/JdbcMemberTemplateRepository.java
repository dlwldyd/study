package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcMemberTemplateRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    //dataSource는 스프링이 실행될때 스프링부트가 자동으로 스프링 컨테이너에 등록한다. dataSource에는 DB와의 접속정보가 들어있다.
    //jdbcTemplate는 스프링이 시작할 때 스프링 빈에 등록되는 객체가 아니기 때문에 DI를 다르게 해주어야 한다.
    @Autowired
    public JdbcMemberTemplateRepository(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        //insert 쿼리를 위한 객체 생성, select 쿼리는 jdbcTemplate를 그대로 써도 되지만 insert 쿼리는 따로 객체를 생성해야함
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        /*withTableName에 입력할 테이블의 이름을 넣고 usingGeneratedKeyColumns에는 auto increment로 자동으로 입력되는
        속성명을 넣는다.*/
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        //insert할 속성과 값을 Map에 넣는다.
        parameters.put("name", member.getName());
        //MapSqlParameterSource는 전달할 쿼리를 map의 형태로 전달할 때 쓰인다.
        //executeAndReturnKey는 쿼리를 실행하고 insert의 결과로 생긴 행의 primary key를 반환한다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //쿼리 스트링에서 파라미터 바인딩을 해야하는 부분에는 '?'로 적어둔다.
        //memberRowMapper()는 쿼리의 결과로 나온 ResultSet을 모두 모아 RowMapper객체로 반환한다.
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }
    //RowMapper는 인터페이스기 때문에 ResultSet을 처리하는 mapRow함수를 오버라이드 해야한다.
    private RowMapper<Member> memberRowMapper() {
        /*return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        }*/

        /*rs는 ResultSet객체이다. ResultSet객체는 쿼리의 결과를 저장하는 객체이다.(1개 행만)
        rs.getInt("속성명")은 해당 행의 "속성명"에 해당하는 값을 Int형으로 반환한다.
        getString, getLong, getDouble 등 여러 함수가 있다.
        rowNum은 쿼리의 결과를 rs에 저장하는 것을 rowNum만큼 반복함을 말한다.
        */
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
