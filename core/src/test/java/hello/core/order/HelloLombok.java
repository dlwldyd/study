package hello.core.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        //setter사용 가능
        helloLombok.setName("aba");
        helloLombok.setAge(12);

        //toString사용가능
        System.out.println("helloLombok = " + helloLombok);
        //getter사용 가능
        String s = helloLombok.getName();
        System.out.println("s = " + s);
        int i = helloLombok.getAge();
        System.out.println("i = " + i);
    }
}
