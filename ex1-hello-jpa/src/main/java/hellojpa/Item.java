package hellojpa;

import javax.persistence.*;

//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//해당 row 가 어떤 서브타입에 속하는지 나타내는 컬럼이 추가된다. 상속관계 매핑 시 사용하는 것이 좋다.(기본값 : DTYPE)
//JOINED 전략에서는 @DiscriminatorColumn 을 안쓰면 DTYPE이 추가가 안된다.
//SINGLE_TABLE 전략에서는 @DiscriminatorColumn 을 안써도 자동으로 DTYPE이 추가된다.(default 값으로)
//TABLE_PER_CLASS 전략에서는 @DiscriminatorColumn 를 써도 의미가 없기 때문에 DTYPE이 추가되지 않는다.
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
