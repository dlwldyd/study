package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //GetMapping("/")는 welcome page에 mapping됨
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
