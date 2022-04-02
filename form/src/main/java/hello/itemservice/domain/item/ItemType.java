package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemType {
    //BOOK, FOOD, ETC 에 해당하는 객체 3개가 만들어지고 더 이상의 객체는 만들어지지 않음
    //만약 enum 의 값이 하나만 있으면 싱글톤 처럼 사용할 수 있음
    //BOOK, FOOD, ETC 는 생성자라 보면됨, 예를 들면 BOOK("도서")은 new ItemType("도서")라 보면됨
    BOOK("도서"), FOOD("음식"), ETC("기타");

    //enum 타입에도 변수, 메서드, 생성자 등을 모두 만들 수 있음
    private final String description;
}
