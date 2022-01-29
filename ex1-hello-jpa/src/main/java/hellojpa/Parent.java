package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    //CascadeType.ALL : Parent 를 persist 할 때 연관된 객체도 전부 persist 된다(remove 도 마찬가지)
    //CascadeType.PERSIST : Parent 를 persist 할 때 연관된 객체도 전부 persist 된다(persist 만)
    //CascadeType.REMOVE : Parent 를 remove 할 때 연관된 객체도 전부 remove 된다(remove 만)
    //나머지는 잘 사용 안함
    //child 엔티티가 parent 엔티티 하나와만 연관관계에 있고 lifecycle 이 같을 때 사용하면된다.(연관관계가 많으면 사용하면 안됨)

    //orphanRemoval : true 일 때 childList 에서 빠진 엔티티는 Child 테이블에서 삭제된다.(@OneToMany, @OneToOne 에만 있음)
    // parent 를 삭제하면 childList 에 있는 모든 child 가 테이블에서 삭제된다.(CascadeType.REMOVE 처럼 동작)
    //child 엔티티가 parent 엔티티와만 연관관계에 있을 때 사용해야한다.
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildList() {
        return childList;
    }
}
