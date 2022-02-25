package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
/*
예외 처리를 하는 메서드를 컨트롤러 내에 정의하면 해당 컨트롤러 내에서 발생한 예외만 처리한다. 따라서 @ControllerAdvice,
@RestControllerAdvice 를 통해 예외 처리에 대한 메서드를 컨트롤러 외부에 따로 정의하면 AOP 를 이용하여 여러 컨트롤러에
예외 처리를 적용할 수 있다. 만약 @ControllerAdvice, @RestControllerAdvice 에 속성을 따로 정의하지 않는다면
모든 컨트롤러에 대해 예외처리가 적용되고, 그러길 원하지 않는다면 속성을 정의해서 내가 원하는 컨트롤러에만 예외 처리를 적용할 수 있다.
굳이 예외처리 로직이 아니더라도 컨트롤러의 로직을 분리해 여러 컨트롤러에 적용하고 싶을 때는 이 어노테이션을 사용하면 된다.
 */
//@ControllerAdvice(annotations = RestController.class) -> 해당 어노테이션이 붙은 컨트롤러만 예외처리됨
//@ControllerAdvice("org.example.controllers") -> 해당 패키지와 그 하위에 있는 컨트롤러만 예외처리됨, 배열로 여러 패키지 지정 가능
//@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class}) -> 특정 타입으로 캐스팅 가능한 컨트롤러만 예외처리됨
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //IllegalArgumentException 과 그 하위 타입의 예외에대한 처리
    /*
    예외처리는 구체적인 타입을 타겟으로 하는 메서드가 우선순위를 가진다.
    예를 들면 Exception 을 처리하는 메서드와 IllegalArgumentException 을 처리하는 메서드가 있고,
    IllegalArgumentException 가 발생한다면 두 메서드는 모두 IllegalArgumentException 을 처리할 수 있지만
    IllegalArgumentException 을 처리하는 메서드가 더 구체적이기 때문에(더 하위 타입) IllegalArgumentException 을
    처리하는 메서드가 해당 예외를 처리하게 된다.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        //JSON 응답을 할 클래스를 내가 따로 정의해둠
        return new ErrorResult("BAD", e.getMessage());
    }

    //어떤 예외에 대한 처리인지 명시하지 않는다면 메서드의 파라밑로 들어가는 예외 타입을 보고 결정한다.
    //UserException 과 그 하위 타입에 대한 처리
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
