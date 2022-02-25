package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
//필터는 Filter를 구현해야함
public class LogFilter implements Filter {

    //필터 초기화할 때 호출됨
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("log filter init");
    }

    //필터의 메인 로직직
   @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}] [{}]", uuid, requestURI);
            //다음 필터 호출, 다음 필터가 없고 필터링 되지 않으면 DispatcherServlet 호출
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        }finally {
            //Controller 에 의해 response 가 반환되고 DispatcherServlet 의 호출이 끝나면 실행
            log.info("RESPONSE [{}] [{}]", uuid, requestURI);
        }
    }

    //필터가 소멸될 때 호출됨
    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("log filter destroy");
    }
}
