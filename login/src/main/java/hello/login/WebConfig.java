package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.argumentresolver.TestArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
//스프링 인터셉터를 등록하려면 WebMvcConfigurer 를 구현해야한다. 서블릿 필터의 경우에는 필요없다.
public class WebConfig implements WebMvcConfigurer {

//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        //내가 만든 필터를 넣어야 한다.
        filterRegistrationBean.setFilter(new LogFilter());
        //필터의 적용순서를 정해야 한다.
        filterRegistrationBean.setOrder(1);
        //적용할 URL 을 정해야 한다. 여러 URL 을 넣을 수 있다. setFilter 호출 후 setOrder, addUrlPatterns 를 호출해야한다.
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean logCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        //내가 만든 필터를 넣어야 한다.
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        //필터의 적용순서를 정해야 한다.
        filterRegistrationBean.setOrder(2);
        //적용할 URL 을 정해야 한다. 여러 URL 을 넣을 수 있다. setFilter 하고 setOrder, addUrlPatterns 를 불러야한다.
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    //WebMvcConfigurer 의 addInterceptors() 메서드를 오버라이드 해서 스프링 인터셉터를 추가할 수 있다.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) // 인터셉터 추가
                .order(1) //인터셉터 순서 정하기
                .addPathPatterns("/**") //ant 패턴으로 가능, 해당 url에 대한 요청은 해당 인터셉터를 호출함
                .excludePathPatterns("/css/**", "/*.ico", "/error"); //ant 패턴, 해당 url은 인터셉터 적용 제외

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/members/add", "/logout", "/css/**", "/*.ico", "/error");
    }

    //커스텀 ArgumentResolver 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TestArgumentResolver());
        resolvers.add(new LoginMemberArgumentResolver());
    }
}
