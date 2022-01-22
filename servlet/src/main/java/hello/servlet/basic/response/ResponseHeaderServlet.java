package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //응답코드 넣을 수 있음(status-line)
        //response.setStatus(200);
        response.setStatus(HttpServletResponse.SC_OK);

        //reponse-header 설정
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setHeader("Content-Type", "text/plain; Charset=utf-8"); 이렇게도 지정 가능
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        //내가 원하는 임의의 헤더도 만들 수 있음
        response.setHeader("My-Header", "hello");
        //쿠키 설정
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600"); 이렇게도 가능

        //redirect 설정
        //response.setStatus(HttpServletResponse.SC_FOUND); //302 not found 코드가 redirect 상태코드
        //response.sendRedirect("/basic/hello-form.html") 이거 쓰려면 status를 302로 설정해야함
        //response.setHeader("Location", "/basic/hello-form.html");이렇게도 가능, 이거 쓰면 302코드로 설정안해도 redirect

        //message body 설정
        response.getWriter().println("OK");//개행문자 있음(System.out.println())
        //response.getWriter().write("OK"); 개행문자 없음(System.out.write())
    }
}
