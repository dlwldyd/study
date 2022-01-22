package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller//해당 객체가 controller임을 나타냄
public class HelloController {
    @GetMapping("hello")//localhost:8080/hello로 HTTP GET을 받으면 아래의 함수를 실행
    public String hello(Model model){
        model.addAttribute("data", "hello!");//model에 키:값 쌍을 넣는다
        return "hello";//templates에 있는 hello.html에 model을 넘기고 실행
    }
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {//Ctrl+p로 파라미터 정보를 볼 수 있다
        /*@RequestParam("name")하면 url에 name이라는 파라미터를 무조건 입력해줘야함
        입력 하든 안하든 실행되게 하려면 @RequestParam(value="name", required=false)로 해줘야함(default는 required=true임
         */
        model.addAttribute("name", name);
        return "hello-template";
    }
    @GetMapping("hello-string")
    @ResponseBody//html을 찾아서 보내주는 것이 아니라 함수의 리턴 값을 HTTP의 body부분에 넣어 그대로 보냄
    //html을 보내면 HTTP의 body부분에 html문서가 담겨서 보내진다(위에는 html태그 같은 것이 없다)
    public String helloString(@RequestParam("name") String name){
        return "hello "+name;
    }
    @GetMapping("hello-api")
    @ResponseBody//객체를 반환하는데 @ResponseBody가 있으면 default로 JSON으로 데이터를 보낸다
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello= new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello{//static으로 클래스를 정의하면 클래스 안에서도 클래스를 정의할 수 있음
        private String name;
        public String getName() {//Alt+insert 누르면 getter, setter 만들 수 있음
            return name;//JSON의 키 :반환하는 객체, 변수의 이름, 값 : 해당 객체, 변수에 들어있는 값
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
/*url로 요청을 받으면 해당 url로 매핑되어있는 controller가 있는지 확인하고 있으면 controller를 통해 templates에 있는
동적페이지가 실행, 없으면 static에 해당 url에 해당하는 페이지가 있는지 확인하고 있으면 해당 정적페이지 실행*/
