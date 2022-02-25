package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//ExceptionResolver 를 구현하기 위해서는 HandlerExceptionResolver 를 구현해야한다.
/*
보통은 스프링이 제공하는 ExceptionResolver 인 ExceptionHandlerExceptionResolver, ResponseStatusExceptionResolver,
DefaultHandlerExceptionResolver 로 전부 해결이 되기 때문에 직접 구현할 일은 거의 없다.
 */
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                //ModelAndView 를 새로 생성해서 리턴하면 터진 예외는 여기서 해결된걸로 봄(WAS 에서 오류페이지 요청이 안올라옴)
                //빈 ModelAndView 를 반환하면 뷰를 렌더링하지 않는다.(보통 api 를 반환해야할 때 빈 ModelAndView 를 반환한다.)
                //ModelAndView 에 View 와 Model 등의 정보를 지정해서 반환하면 뷰를 렌더링해서 클라이언트에게 응답한다.(response.sendError()를 호출 안했을 때)
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        //null 을 반환하면 다음 ExceptionResolver 를 찾아서 실행한다.
        //다음 ExceptionResolver 가 없으면 터진 예외가 그대로 WAS 까지 전달됨
        return null;
    }
}
