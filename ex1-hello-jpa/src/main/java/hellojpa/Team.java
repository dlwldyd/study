package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    /*
    mappedBy : 양방향 연관관계에서 연관관계의 주인을 정한다. 데이터베이스의 경우에는 양방향 연관관계가 TEAM_ID 라는
    외래키 하나만으로 성립된다. 하지만 객체에서는 Team 의 경우에는 List<Members> members, Member 의 경우에는
    Team team 에 의해 양방향 연관관계가 성립한다. 따라서 데이터베이스의 경우에는 TEAM_ID 라는 외래키를 바꾸는 것만으로
    엔티티간의 연관관계를 수정할 수 있지만, 객체에서는 만약 연관관계의 주인을 정하지 않는다면 List<Members> members 와
    Team team 중 어떤 것을 수정해서 엔티티간의 연관관계를 수정할 수 있는지 알 수 없다. 그렇기 때문에 연관관계의 주인을 정하여
    List<Members> members 와 Team team 중 어떤 것을 수정하였을 때 데이터베이스에 변화가 일어날 지 정해야 한다.
    이 때 연관관계의 주인만이 외래키를 등록, 수정할 수 있고 주인이 아닌 쪽은 읽기만 가능하다. 그리고 연관관계의 주인이 아닌 쪽에서의
    수정은 객체 상으로는 수정 가능하지만 데이터베이스에 적용할 때 JPA 에서 관련된 쿼리를 내보내지 않는다.
    외래키를 수정할 때는 연관관계의 주인만 수정하는게 아니라 양쪽 다 수정하는 것이 좋다. 왜냐하면 연관관계의 주인만 수정하고
    연관관계의 주인이 아닌 쪽의 객체를 persist 했을 때, 해당 객체는 db에 저장되는게 아니라 영속성 컨테이너의 1차 캐시에
    저장되고 이 때 List<Member> members 는 비어있다. 그리고 해당 객체를 find 로 찾아보면 List<Member> members 는
    그대로 비어있다. 그렇기 때문에 이러한 영속성 컨테이너로 인한 연관관계의 불일치를 해결하기 위하여 양쪽을 모두 수정하는 것이 좋다.
    (객체 지향적으로 생각해도 둘 다 수정하는게 맞다.) 연관관계의 주인은 mappedBy 를 사용하지 않고, 주인이 아닌 쪽은
    mappedBy 속성에 연관관계의 주인의 이름(필드 이름)을 추가해야 한다. 연관관계의 주인을 정할 때는
    외래키가 있는 곳(테이블 상으로)(n:1 중 n에 해당)을 연관관계의 주인으로 정하는 것이 좋다.
     */
    private List<Member2> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member2> getMembers() {
        return members;
    }
}