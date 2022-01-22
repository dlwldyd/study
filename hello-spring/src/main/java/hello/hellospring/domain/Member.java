package hello.hellospring.domain;

import javax.persistence.*;

@Entity//JPA가 관리하는 엔티티임을 나타냄
public class Member {

    /*
    @Id는 아래의 변수가 primary key임을 나타냄
    @GeneratedValue(strategy=GenerationType.IDENTITY)는 아래의 변수가 DB에서 자동으로 생성되고
    생성되는 전략이 IDENTITY(데이터베이스에 키 생성방법을 위임)임을 나타낸다.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
