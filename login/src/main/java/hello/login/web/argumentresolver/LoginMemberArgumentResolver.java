package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
//커스텀 ArgumentResolver 를 만들 때 HandlerMethodArgumentResolver 를 구현해야한다.
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    //이 메서드가 참이 되면 ArgumentResolver 가 사용된다.(아래의 resolveArgument() 메서드가 호출됨)
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        //해당 파라미터에 @Login 어노테이션이 붙어있고, 해당 파라미터가 Member 타입으로 캐스팅이 가능해야함
        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        /*
        MethodParameter parameter : ArgumentResolver 를 통해 바인딩할 파라미터 정보가 들어있음
        NativeWebRequest webRequest : 현재 request 객체, 요청이 HttpServletRequest 로 왔으면 getNativeRequest()로
                                      Object 타입으로 변환 후 HttpServletRequest 로 캐스팅 가능
        ModelAndViewContainer mavContainer : 현재 요청에 대한 ModelAndView 가 들어있는 컨테이너
        WebDataBinderFactory binderFactory : WebDataBinder 인스턴스 생성을 위한 Factory
                                             WebDataBinder 인스턴스 생성 후 Validator 추가 가능
         */

        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        //실제로 바인딩을 한 객체를 반환한다.
        return session.getAttribute(SessionConst.Login_Member);
    }
}
