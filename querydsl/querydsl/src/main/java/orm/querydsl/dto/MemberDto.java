package orm.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;

    //@QueryProjection 어노테이션을 생성자에 붙여주고 compileQuerydsl 을 실행하면 QMemberDto 가 생성된다.
    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}

