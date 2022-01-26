package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 양방향 매핑 시 무한루프 조심
// 양방향 연관관계는 왠만하면 사용하지 않는 것이 좋다.
@Entity
@Table(name = "Member")
public class Member2 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne //member 가 many, team 은 one(n:1 연관관계)
    @JoinColumn(name = "TEAM_ID") //join 하는 컬럼이름(Member2의 외래키 컬럼 이름이 TEAM_ID가 됨)
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    /*
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();
     */

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}