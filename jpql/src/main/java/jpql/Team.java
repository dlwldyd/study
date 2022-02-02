package jpql;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    // N+1문제를 해결하기 위한 어노테이션이다.
    /*
    지연로딩 전략에서 select t from Team t 이라는 쿼리를 날리면 나중에 team.getMembers() 를 할 때 Member 테이블
    에 대한 쿼리가 날라간다. 이 때 N개의 team.getMembers() 를 하면 N개의 쿼리가 날라간다. 하지만 @BatchSize 를 사용하면
    team.getMembers() 를 할 때 원하는 member 뿐만 아니라 @BatchSize 에서 정한 size 만큼 member 를 미리 받아온다.
    (select m from from Member where m.team_id = ?(t.id) -> select m from Member m where m.team_id in (?, ?, ...))
     */
    /*
    persistence.xml 에 <property name="hibernate.default_batch_fetch_size" value="100" /> 을 추가하거나
    application.properties 에 hibernate.default_batch_fetch_size=100 을 추가하면 글로벌 설정으로 설정할 수 있다.
    실무에서는 보통 글로벌 설정으로 가져간다.
     */
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

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

    public List<Member> getMembers() {
        return members;
    }
}
