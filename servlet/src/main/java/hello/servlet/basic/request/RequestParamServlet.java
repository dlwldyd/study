package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //전체 파라미터 조회
        request.getParameterNames().asIterator().forEachRemaining(paramName ->
                System.out.println(paramName + " = " + request.getParameter(paramName)));
        System.out.println();

        //단일 파라미터 조회
        System.out.println("username = " + request.getParameter("username"));
        System.out.println("age = " + request.getParameter("age"));
        System.out.println();

        //이름이 같은 복수 파라미터 조회 ex)localhost:8080/request-param?username=hello1&username=hello2
        String[] usernames = request.getParameterValues("username");
        for (String username : usernames) {
            System.out.println("username = " + username);
        }
    }
}
