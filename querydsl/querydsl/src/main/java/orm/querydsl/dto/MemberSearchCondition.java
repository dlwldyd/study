package orm.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {
    //필터링 조건

    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}
