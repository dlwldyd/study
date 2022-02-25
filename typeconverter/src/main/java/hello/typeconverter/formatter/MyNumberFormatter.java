package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
//문자를 목표타입으로, 목표타입을 문자로 바꿀 때 쓰는 특화된 기능을 가진 타입 컨버터라 보면 된다.
//Formatter<목표타입> 을 구현해서 만들 수 있다.
public class MyNumberFormatter implements Formatter<Number> {

    //문자를 객체로 변경
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text={}, locale={}", text, locale);
        //"1,000" -> 1000
        //NumberFormat.getInstance()는 팩토리 패턴으로 NumberFormat 객체를 얻는 방법임 new 를 써서 얻을 수 없음
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);//Long 타입 리턴됨(Integer 타입 같은걸로 리턴되면 Long 이 받을 수 없으니깐)
    }

    //객체를 문자로 변경
    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        NumberFormat instance = NumberFormat.getInstance(locale);
        return instance.format(object);
    }
}
