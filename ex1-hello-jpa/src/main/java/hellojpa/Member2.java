package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 양방향 매핑 시 무한루프 조심
// 양방향 연관관계는 왠만하면 사용하지 않는 것이 좋다.
@Entity
@Table(name = "Member")
public class Member2 extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    //FetchType.LAZY 를 하면 실제 팀 테이블을 조인해서 엔티티를 가져오는게 아니라 프록시를 가져온다.
    //실제 팀 엔티티에 있는 값을 가져올 때 프록시에 있는 target 이 초기화 된다.(팀을 조회하는 쿼리가 나감)

    //FetchType.EAGER 를 하면 실제 팀 테이블을 조인해서 엔티티를 가져온다.
    @ManyToOne(fetch = FetchType.LAZY) //member 가 many, team 은 one(n:1 연관관계)
    @JoinColumn(name = "TEAM_ID") //join 하는 컬럼이름(Member2의 외래키 컬럼 이름이 TEAM_ID가 됨)
    private Team team;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String street;
    private String city;
    private String zipcode;

    /*
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
     */

    /*
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();
     */

    /*
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();
     */

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