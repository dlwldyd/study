package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    //@Validated 가 붙은 객체에 대해 supports() 메서드가 참을 반환하면 검증을 한다.(validate() 메서드 호출)
    @Override
    public boolean supports(Class<?> clazz) {
        //Item 타입으로 캐스팅 가능하면 참을 반환함
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    //Errors 는 BindingResult 의 부모 클래스
    //target 에는 검증할 데이터가 들어온다.
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //rejectValue -> errors 에 필드에러 추가
            errors.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }


        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //reject -> errors 에 글로벌 에러 추가(그래서 파라미터로 필드 타입을 받지 않음)
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
