package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MappingController {

    //배열로 여러개의 URI를 넣을 수 도 있음
    @RequestMapping(value = {"/hello-basic", "/hello-go"})
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용
     * GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기)
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable userId
     * URI 형식이 /mapping/kim 과 같은 형식으로 옴
     * @PathVariable 을 이용해서 꺼낼 수 있음
     * 매우 많이 사용
     */
    @GetMapping("/mapping/{userId}")
    //@PathVariable("userId") String data -> @PathVariable String userId 변수명 맞추면 이렇게 써도됨
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    /**
     * PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode" -> 파라미터 이름만 넣어도됨
     * params="!mode" -> 특정 파라미터가 없어야 호출되도록 할 수 있음
     * params="mode=debug" -> 파라미터의 값을 요구할 수 있음
     * params="mode!=debug" (! = ) -> 파라미터의 값이 특정 값이 아님을 요구할 수 있음
     * params = {"mode=debug","data=good"} -> 여러개도 지정 가능
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    //localhost:8080/mapping-param?mode=debug 라는 URL로 요청이 와야(파라미터 필수) 호출됨
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode", -> 특정 헤더 필드를 요구할 수 있음
     * headers="!mode" -> 특정 헤더 필드가 없어야 하는 것을 요구할 수 있음
     * headers="mode=debug" -> 특정 헤더 필드의 특정 값을 요구 할 수 있음
     * headers="mode!=debug" (! = ) -> 특정 헤더 필드가 특정 값을 가지지 않도록 요구 할 수 있음
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 의 경우 위의 headers 를 쓰면 안됨
     * Content-Type 이 특정 값을 가지면 호출하도록 할 수 있음
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * consumes={"text/plain", "application/*"}
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     * produces = {"text/plain", "application/*"}
     * produces = "text/plain;charset=UTF-8"
     * produces = MediaType.TEXT_PLAIN_VALUE
     * q값이 높은 쪽이 mapping 됨
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProducesHtml() {
        log.info("mappingProducesHtml");
        return "ok html";
    }
    @PostMapping(value = "/mapping-produce", produces = "application/json")
    public String mappingProducesJson() {
        log.info("mappingProducesJson");
        return "ok json";
    }
}
