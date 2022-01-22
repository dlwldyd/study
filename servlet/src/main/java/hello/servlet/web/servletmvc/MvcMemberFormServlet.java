package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //디스패처는 /webapp 부터 찾는다.
        String viewPath = "/WEB-INF/views/new-form.jsp";
        //서블릿(컨트롤러)에서 jsp파일(뷰)로 이동할때 사용
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        //서블릿에서 jsp파일을 호출, 다른 서블릿이나 jsp로 이동할 수 있는 기능이다.(redirect 아님)
        dispatcher.forward(request, response);
    }
}
