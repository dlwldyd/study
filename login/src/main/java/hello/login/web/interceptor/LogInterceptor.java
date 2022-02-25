package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
//스프링 인터셉터는 HandlerInterceptor 를 구현해야한다.
public class LogInterceptor implements HandlerInterceptor {


    public static final String LOG_ID = "logId";

    //컨트롤러 호출 전에 호출된다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping 사용 시 HandlerMethod 가 넘어옴
        //정적 리소스 사용 시 ResourceHttpRequestHandler 가 넘어옴
        if (handler instanceof HandlerMethod) {
            //호출한 컨트롤러 메서드의 모든 정보가 포함되어 있다.
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}] [{}] [{}]", uuid, requestURI, handler);

        return true;
    }

    //컨트롤러 호출 후 호출된다. 컨트롤러에서 예외가 발생하면 postHandle 은 호출되지 않는다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    //컨트롤러 호출 후 호출된다. : afterCompletion 은 항상 호출된다. 이 경우 예외( ex )를 파라미터로 받아서 어떤 예외가 발생했는지 로그로 출력할 수 있다.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}] [{}] [{}]", logId, requestURI, handler);
        if (ex != null) {
            //오류는 {}를 안넣어도 됨
            log.error("afterCompletion error!!", ex);
        }
    }
}
