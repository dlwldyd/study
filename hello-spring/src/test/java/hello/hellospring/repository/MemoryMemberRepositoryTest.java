package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;//static import

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository=new MemoryMemberRepository();
    @AfterEach//테스트코드가 끝날 때 마다 실행됨
    public void afterEach(){
        repository.clearStore();
        //테스트코드의 순서는 보장되지 않기 때문에 순서에 따라 테스트 결과가 달라지지 않도록 테스트가 끝나면 store를 비워줌
    }
    @Test//테스트 코드임을 나타냄
    public void save(){
        Member member=new Member();
        member.setName("spring");
        repository.save(member);
        Member result=repository.findById(member.getId()).get();
        //Assertions.assertEquals(member, result); => Junit, 밑에게 가시성이 좋아서 밑에걸 씀
        //Assertions.assertThat(member).isEqualTo(result); //원래 써야하는 문법
        assertThat(member).isEqualTo(result); // static import(위에있음)을 사용하면 Assertions 생략가능(Alt+Enter로 import시킬 수 있음)
        //result값이 member값과 같은지를 비교함
    }
    @Test
    public void findByName(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result=repository.findByName("spring1").get();
        assertThat(member1).isEqualTo(result);
        result=repository.findByName("spring2").get();
        assertThat(member2).isEqualTo(result);
    }
    @Test
    public void findAll(){
        Member member1=new Member();
        Member member2=new Member();
        member1.setName("spring1");
        member2.setName("spring2");
        repository.save(member1);
        repository.save(member2);
        List<Member> result = repository.findAll();
    }
}