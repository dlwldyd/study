package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //private final ObjectProvider<MyLogger> objectProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        //쓰레드별로 MyLogger가 생성됨
        //MyLogger myLogger = objectProvider.getObject();
        String requestUrl = request.getRequestURL().toString();
        //myLogger 기능이 호출되기 전까지는 프록시 객체가 주입되어있다.
        System.out.println("myLogger = " + myLogger.getClass());
        //myLogger 기능을 호출할때 프로시가 진짜 myLogger의 해당 기능을 빈에서 찾아서 동작시켜준다.
        myLogger.setRequestUrl(requestUrl);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
