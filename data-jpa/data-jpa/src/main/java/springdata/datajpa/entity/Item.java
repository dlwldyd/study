package springdata.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*
JpaRepository 의 구현체인 SimpleJpaRepository 의 save 메서드를 보면 엔티티가 새로 생성된 엔티티이면 persist()를 하고 그렇지 않으면 merge()를 한다.
새로 생성된 엔티티인지 아닌지를 판단할 때는 엔티티의 식별자가 @GeneratedValue 어노테이션을 통해 자동 생성된다고 가정하고
해당 엔티티의 식별자를 보고 판단하는데, 만약 식별자가 primitive 타입일 때는 식별자가 0이면 새로 생성된 엔티티이고 0이 아니면
새로 생성된 엔티티가 아니라 판단한다. 그리고 primitive 타입이 아닌 경우에는 식별자가 null이면 새로 생성된 엔티티이고 null이
아니면 새로 생성된 엔티티가 아니라 판단한다. 하지만 만약 엔티티의 식별자가 @GeneratedValue 어노테이션을 통해 자동 생성되는
식별자가 아니라면 이러한 메커니즘으로 엔티티가 새로생성된 엔티티인지 판단할 수 없다.
 */
//persist()를 호출해도 되는데 merge()를 호출하는 상황일 때 문제를 해결하기 위하여 Persistable<>를 구현한다.
//제네릭은 엔티티의 식별자 타입
public class Item implements Persistable<String> {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    public Item(String id) {
        this.id = id;
    }

    //새로 생성된 엔티티인지 아닌지를 판단하는 메서드
    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
