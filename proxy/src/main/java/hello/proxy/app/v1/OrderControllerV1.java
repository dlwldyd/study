package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping // 컨트롤러를 인터페이스로 만들고 @Bean 으로 등록할 때 @RequestMapping 을 꼭 써줘야함
@ResponseBody
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    //인터페이스에서는 어노테이션이 매핑되는 이름(여기서는 itemId)가 있어야 한다.
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
