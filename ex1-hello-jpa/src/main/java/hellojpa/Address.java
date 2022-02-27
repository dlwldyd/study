package hellojpa;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    private String street;
    private String city;
    private String zipcode;

    //값 타입이 임베디드 타입일 경우 기본생성자 필수
    public Address() {
    }

    public Address(String street, String city, String zipcode) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    /*
    임베디드 타입을 만들 때는 setter 를 만들지 않고 생성자로만 객체의 값을 설정할 수 있게 만들어야 한다.
    왜냐하면 같은 임베디드 타입 객체를 여러 엔티티에서 사용하게 되면 하나의 객체를 여러 엔티티가 공유하게 된다.
    아무리 개발자가 이러한 공유 문제를 신경써서 개발한다 할지라도 이러한 같은 객체의 공유 문제는 언제든지 발생할 수 있다.
    따라서 setter 자체를 만들지 않고 생성자로만 값을 설정할 수 있게 만들면 만약 여러 엔티티에서 하나의 임베디드 타입 객체를
    공유한다 할지라도 해당 객체를 setter 를 통해 값을 변경할 수 없기 때문에 객체의 공유로 인해 하나의 엔티티 값을 바꿨는데
    다른 엔티티 값이 바뀌는 것과 같은 문제가 발생하지 않는다.
     */
    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, zipcode);
    }
}
