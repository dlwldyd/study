package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void RequestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);
        response.getWriter().write("ok");
    }

    //리턴값에 해당하는 리소스를 찾는 것이 아니라 리턴값을 http body 에 넣어서 반환함
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String RequestParamV2(
            @RequestParam("username")String memberName,
            @RequestParam("age")int memberAge){
        log.info("username = {}, age = {}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String RequestParamV3(@RequestParam String username, @RequestParam int age){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    //단순 타입(String, int, Integer 등)일 때만 @RequestParam 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String RequestParamV4(String username, int age){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    //required=false 세팅하면 해당 파라미터가 없어도 오류 안냄, required=true 가 default
    //파라미터가 없으면 null 이 들어감 int 형으로 하면 null 을 넣을 수 없기 때문에 Integer 를 쓰거나 defaultValue 를 써야함
    //빈문자가 들어오면 값이 빈문자라는 값이 들어온 것으로 처리됨
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String RequestParamRequired(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer age){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    //defaultValue 를 쓰면 required=false 가 필요가 없음
    //빈문자가 들어와도 defaultValue 값으로 세팅됨
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String RequestParamDefault(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") int age){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    //모든 파라미터를 맵이나 MultiValueMap 으로 받을 수 있음. 왠만하면 MultiValueMap 을 사용하는게 좋음
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String RequestParamMap(@RequestParam MultiValueMap<String, Object> paramMap){

        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /*
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String ModelAttributeV1(@RequestParam String username, @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        //toString() 함수가 있으면 이렇게도 쓸 수 있음
        log.info("helloData = {}", helloData);

        return "ok";
    }
    */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    //helloData 의 로컬변수 이름과 파라미터의 이름이 같아야 하고 setter 가 세팅되어 있어야함
    public String ModelAttributeV1(@ModelAttribute HelloData helloData) {

        log.info("helloData = {}", helloData);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    /*
    단순타입이면 @RequestParam 이 호출(?)되고 그렇지 않으면 Argument Resolver 로 지정해 둔 타입을
    제외하고(HttpServletRequest 같은 예약된 객체)@ModelAttribute 가 호출된다.
     */
    public String ModelAttributeV2(HelloData helloData) {

        log.info("helloData = {}", helloData);

        return "ok";
    }
}
