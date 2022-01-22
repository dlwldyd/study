package hello.springmvc.basic;

import lombok.Data;

import java.security.PrivateKey;

//@Getter, @Setter, @ToString, @EqualAndHashCode, @RequiredArgsConstructor 를 전부 붙이는 것과 같음
@Data
public class HelloData {
    private String username;
    private int age;
}
