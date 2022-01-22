package hello.core.member;

import hello.core.annotation.MainDiscountPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
/*
해당 스프링빈에 subMemberRepository라는 이름으로 찾을 수 있는 추가적인 식별자를 붙인다. 빈 이름이 바뀐건 아니다.
직접 빈을 등록할때도 @Qualifier를 넣을 수 있다.
 */
//@Qualifier("subMemberRepository")

//@Primary가 지정되있으면 같은 타입의 빈이 여러개 있을때 @Primary가 설정된 빈이 할당된다.
//@Qualifier가 @Primary보다 우선순위가 높다.
@Primary
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
