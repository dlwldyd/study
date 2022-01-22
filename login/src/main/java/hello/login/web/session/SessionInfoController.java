package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String SessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name = {}, value = {}", name, session.getAttribute(name)));
        //세션 id 출력
        log.info("session id = {}", session.getId());
        //세션을 비활성화 시키는 시간간격(초), 일정 시간 이상 접속하지 않으면 세션을 비활성화 시킴
        log.info("getMaxInactiveInterval = {}", session.getMaxInactiveInterval());
        //세션 생성 시간 출력
        log.info("CreationTime = {}", new Date(session.getCreationTime()));
        //마지막 세션 접속 시간 출력
        log.info("LastAccessedTime() = {}", new Date(session.getLastAccessedTime()));
        //새로 생성한 세션인지
        log.info("isNew = {}", session.isNew());

        return "세션 출력";
    }
}
