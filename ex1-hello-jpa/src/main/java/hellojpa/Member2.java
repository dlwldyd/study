package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /*
    @Embedded
    private Period workPeriod;
     */

    @Embedded
    private Address homeAddress;

//    @Embedded
    /*
    같은 임베디드 타입을 사용할 때는 그대로 사용하면 컬럼명이 중복되 예외가 발생하기 때문에 @AttributeOverrides 를
    해서 컬럼명을 바꿔줘야 한다.
     */
    /*
    @AttributeOverrides(
            {@AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))})
    private Address workAddress;
    */

    // 값 타입 컬렉션의 경우 별도의 테이블이 생성됨
    // 값 타입 컬렉션은 값 타입 컬렉션을 포함하는 엔티티와 lifecycle 을 함께한다.
    // 값 타입 컬렉션은 지연로딩 전략을 기본으로 사용한다.
    /*
    엔티티와의 차이점은 엔티티는 기본키가 될 ID를 따로 만들어 줄 수 있지만
    값타입 컬렉션은 전체 컬럼이 기본키가 되고 ID를 만들어 줄 수 없음
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME") // 임베디드 타입이 아니면 @Column 사용해 컬럼명 변경 가능
    private Set<String> favoriteFoods = new HashSet<>();

    //List 는 쓰면 안됨(엔티티를 하나 만들어서 일대다 매핑과 CascadeType.ALL, orphanRemoval 을 활용하는게 더 좋음)
    //값 타입이 복잡할 경우에도 컬렉션으로 쓰면 안됨
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    // 임베디드 타입일 경우에는 @AttributeOverride 를 통해 컬럼명 변경 가능
    @AttributeOverrides(
            {@AttributeOverride(name = "city", column = @Column(name = "CITY")),
                    @AttributeOverride(name = "street", column = @Column(name = "STREET")),
                    @AttributeOverride(name = "zipcode", column = @Column(name = "ZIPCODE"))})
    private List<Address> addressHistory = new ArrayList<>();

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

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }
}