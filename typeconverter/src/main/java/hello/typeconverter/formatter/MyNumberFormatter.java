package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text={}, locale={}", text, locale);
        //"1,000" -> 1000
        //NumberFormat.getInstance()는 팩토리 패턴으로 NumberFormat 객체를 얻는 방법임 new 를 써서 얻을 수 없음
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);//Long 타입 리턴됨(Integer 타입 같은걸로 리턴되면 Long 이 받을 수 없으니깐)
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        NumberFormat instance = NumberFormat.getInstance(locale);
        return instance.format(object);
    }
}
