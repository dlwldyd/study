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
    //MemberDto 클래스는 QueryDSL 라이브러리에 의존적이게 되지만 더 간단하게 DTO 반환을 할 수 있게 된다.
    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}

