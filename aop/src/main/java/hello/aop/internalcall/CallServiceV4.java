package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV4 {

    public void external() {
        log.info("call external");
    }

    public void internal() {
        log.info("call internal");
    }
}
