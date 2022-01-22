package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {//접근지정자 아무 것도 안쓰면 기본적으로 public
    Member save(Member member);//Repository에 member를 저장
    Optional<Member> findById(Long id);//id로 member를 찾아서 반환
    Optional<Member> findByName(String name);//name으로 member를 찾아서 반환
    List<Member> findAll();//모든 회원을 반환
}
