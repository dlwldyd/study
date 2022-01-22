package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@Controller는 리턴된 문자열에 해당하는 리소스(뷰)를 찾아서 반환한다.
@RestController는 리소스를 찾지 않고 문자열을 바디에 넣어서 반환한다.
여기서 Rest는 Restful API에서의 Rest임
 */
@RestController
@Slf4j//private final Logger log = LoggerFactory.getLogger(getClass()); 이거 쓸 필요 없음(Lombok)
public class LogTestController {

    //getClass()==LogTestController.class
    //private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "spring";

        /*
        출력형식
        (실행한 시간) (로그레벨) (프로세스 ID) --- (실행한 쓰레드 명) (클래스 명) : (로그)
        log.info("info log={}", name); : {}가 name으로 치환됨 -> info log=(name)으로 출력
        각 함수는 로그레벨 아래로 내려갈수록 중요도가 높음
         */
        log.trace("info log={}", name);
        log.debug("info log={}", name);
        log.info("info log={}", name);
        log.warn("info log={}", name);
        log.error("info log={}", name);
        //log.trace("info log="+ name);이렇게 사용하면 안됨 로그를 안찍는 경우에도 "info log="+ name 연산이 일어나기 때문

        return "ok";
    }
}
