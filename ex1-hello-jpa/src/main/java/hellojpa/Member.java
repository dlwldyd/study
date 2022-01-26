package hellojpa;

import javax.persistence.*;
import java.util.Date;

//@Entity
//@Table(name = "USER") : 엔티티가 저장될 테이블 이름을 지정할 수 있다.
@SequenceGenerator( // SequenceGenerator 생성(직접 데이터베이스에서 create 할 수도 있음)
        name = "MEMBER_SEQ_GENERATOR", // SequenceGenerator 이름(JPA 에서 사용)
        sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 SequenceGenerator 이름(데이터베이스에서의 이름)
        initialValue = 1, allocationSize = 2) // initialValue 기본값 : 1, allocationSize 기본값 : 50
/*
@TableGenerator( // TableGenerator(키 생성 전용 테이블) 생성(직접 데이터베이스에서 create 할 수도 있음)(잘 안씀)
        name = "MEMBER_SEQ_GENERATOR", // TableGenerator 이름(JPA 에서 사용)
        table = "MY_SEQUENCES", // TableGenerator(키 생성 전용 테이블) 이름
        pkColumnValue = "MEMBER_SEQ", allocationSize = 2)  // pkColumnValue : 기본키 값을 지정(컬럼 이름이 아니라 데이터로 들어가는 값)

        allocationSize : 처음 persist 를 하면 키 값이 없기 때문에 영속 상태로 만들 수가 없다. 따라서 키 값을 데이터베이스에
        쿼리를 보내서 받아와야한다. 이 때 쿼리를 보내 테이블(시퀀스)로 부터 값을 받아 오는데 만약 persist 를 할 때마다
        쿼리를 보내면 성능상 좋지 않을 것이다. 따라서 만약 allocationSize = 50 일 때 쿼리를 보내면 테이블(시퀀스)의
        값(MEMBER_SEQ, MEMBER_SEQ)은 50증가하고 영속석 컨테이너에서는 다음 50개의 persist 까지는 데이터베이스에
        쿼리를 보내지 않고 50개까지 키를 할당할 수 있다. 하지만 allocationSize 를 너무 크게 잡으면 만약 어플리케이션을
        종료했을 때 많게는 allocationSize 만큼 키값의 공백이 생겨날 수 있다.(쿼리를 보내고 allocationSize 중 얼만큼을
        사용했는지는 메모리에 저장되어 있기 때문) 그렇기 때문에 allocationSize 를 너무 크게 잡는 것도 좋지 않다.
 */
public class Member {

    //엔티티의 필드에 붙이는 어노테이션은 멤버면수를 db와 매핑시킬 때 필요한 정보이다.

    // 기본키 매핑, 기본키를 직접 할당 하려면 @Id만 사용하면 된다.
    // strategy : 기본키 생성 전략을 정한다.
    // GenerationType.AUTO : db 방언에 맞춰서 기본키를 생성한다.(기본값)
    // GenerationType.IDENTITY : 기본키 생성을 데이터베이스에 위임한다.
    // GenerationType.SEQUENCE : sequence 객체를 통해 기본키 생성
    // GenerationType.TABLE : 키 생성 전용 테이블을 만들어서 SEQUENCE 전략처럼 사용(데이터베이스에 따라 SEQUENCE 를 사용하지 못할 수 있기 때문에)
    // generator : SequenceGenerator 나 TableGenerator 를 지정할 수 있음(저거 2개 사용시 필수)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 컬럼 이름을 지정할 수 있다.
    // insertable : 엔티티를 insert 할 때 해당 필드는 insert 하지 않는다(db 에서 설정된 default 값이 들어간다)(기본값 true)(읽기 전용일 때 사용)
    // updatable : 엔티티의 해당 필드가 바뀌면 update 쿼리를 db에 날릴지 결정(기본값 true)(읽기 전용일 때 사용)
    // nullable : 해당 필드에 null 을 넣을 수 있는지 결정(기본값 true)
    // length : varchar 길이 결정(기본값 255)(String 타입에서 사용)
    // columnDefinition : 컬럼 정보를 직접 넣을 수 있다.(예시 : columnDefinition =  "varchar(100) default 'EMPTY'")
    // precision : BigDecimal 타입이나 BigInteger 타입에서 사용한다. 숫자의 자리수를 정한다. 소수점을 포함한 자리수이다. scale 과 함께 사용한다.
    // scale : BigDecimal 타입이나 BigInteger 타입에서 사용한다. 소수점의 자리수를 정한다. precision 과 함께 사용한다.(예시 : precision=19, scale=8)
    @Column(name = "name")
    private String username;

    private Integer age;

    // enum 타입을 쓸 때 붙인다. db 에는 enum 과 같은 타입이 없기 때문(기본값 EnumType.ORDINAL)
    // EnumType.ORDINAL : enum 의 순서를 db에 저장(USER=0, ADMIN=1에서 1, 2를 저장) -> 사용 안함
    // EnumType.STRING : enum 의 이름을 db에 저장(USER=0, ADMIN=1에서 USER, ADMIN 을 저장)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // Date 타입을 사용할 때 붙인다. 자바에는 Date 안에 날짜와 시간이 전부 들어가 있지만 데이터베이스에는 날짜만 사용하거나 시간만 사용하거나 둘 다 사용할 수 있다.
    // 자바8 부터는 Date 대신 LocalDate, LocalTime, LocalDateTime 을 쓰면 어노테이션 안붙여도 자동으로 매핑된다.
    // TemporalType.DATE : 날짜만 사용(LocalDate)
    // TemporalType.TIME : 시간만 사용(LocalTime)
    // TemporalType.TIMESTAMP : 날짜와 시간 둘다 사용(LocalDateTime)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // VARCHAR 를 넘어서는 큰 데이터를 넘기고 싶을 때는 @Lob 을 쓴다.(Large Object)
    // LOB 의 종류에는 BLOB(바이너리 데이터), CLOB(텍스트, 보통 4000자를 넘어가는 경우 CLOB 을 사용한다) 등이 있다.
    // 어떠한 LOB 이 사용될지는 변수 타입에 의해 결정된다.(문자면 CLOB, 아니면 BLOB)
    @Lob
    private String description;

    // 매핑되지 않음
    @Transient
    private int tmp;

    //jpa 에서 엔티티로 사용시 기본 생성자 필수
    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }
}
