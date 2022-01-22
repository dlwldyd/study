package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/*
Component scan을 통해 @Component가 설정되어있으면 스프링이 실행될때 객체를 생성해스프링 컨테이너(스프링 빈)에 자동으로 등록한다.
@Repository는 @Component의 하위 annotation으로 스프링이 실행될때 객체를 생성해서 스프링 빈에 자동으로 등록한다.
스프링 빈에 등록할때 default로 싱글톤으로 등록된다.
@Component는 HelloSpringApplication(main함수가 있는 클래스)의 하위패키지에 있을때만 자동으로 스프링 빈에 등록된다.
*/
//@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store =new HashMap<>();
    private static long sequence=0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //ofNullable함수는 안의 매개변수를 Optional 객체로 감싼다
        //of함수도 Optional 객체로 감싸주지만 안의 매개변수가 null이면 안된다.
        //Optional 객체로 감싸면 null이 반환되더라도 클라이언트에서 그것을 이용해 어떤 작업을 할 수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
        //Map.value()를 하면 Collection을 리턴한다, Collection은 모든 collection framework(List, Set, Map)의 상위인터페이스
        //Collection을 리턴하는데 Map<key, value>에서 Collection<value>를 리턴한다
        //Collection.stream()을 하면 stream객체를 반환
        //stream객체는 collection 객체를 for문을 돌면서 다루는 대신에 람다식을 이용해 코드양을 줄이고 간단하게 표현하기 위해 사용됨
        //filter()함수는 괄호내의 람다식이 참인 요소만 포함된 stream 객체를 반환함
        /*
        findFirst()함수는 filter()로 반환된 stream에서 가장 앞의 요소를 Optional로 반환한다 비어있으면
        아무값도 가지지 않는 비어있는 Optional객체를 반환한다.
        findAny()함수또한 findFIrst()와 같다 단, findAny()는 parallelStream()이나 Stream.parallel()로 생성된
        병렬로 처리되는 Stream의 경우에 아무요소나 Optional로 반환한다.
         */
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore(){
        store.clear();
    }
}
