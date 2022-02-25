package hello.typeconverter;

import hello.typeconverter.converter.IntegerToStringConverter;
import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIntegerConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//타입 컨버터나 포맷터를 추가하고 싶으면 WebMvcConfigurer 의 addFormatters 를 오버라이드 해야함
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        //컨버터가 포맷터보다 우선수위가 높기 때문에 위에 컨버터를 주석처리했음
        //만약 같은 변환을 하는 컨버터가 있다면 컨버터가 실행됨
        registry.addFormatter(new MyNumberFormatter());
    }
}
