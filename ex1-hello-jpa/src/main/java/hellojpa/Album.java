package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
//슈퍼 타입의 row 가 어떤 서브타입에 해당하는 지 나타내는 컬럼(DTYPE)에 들어갈 값을 지정한다.(기본값 : 엔티티 이름)
@DiscriminatorValue("ALBUM")
public class Album extends Item {

    private String artist;
}
