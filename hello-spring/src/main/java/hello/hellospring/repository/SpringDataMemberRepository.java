package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//인터페이스가 인터페이스를 받을때는 extends를 쓴다.
//JpaRepository<T, ID>에서 T는 테이블에 들어가는 객체이름(엔티티 이름)이고 ID는 엔티티의 primary key의 타입이다.
/*
JpaRepository를 상속받으면 스프링이 시작될때 스프링데이터 JPA가 자동으로 JpaRepository를 상속한 인터페이스를 이용해
객체를 생성하여 스프링 빈에 등록한다. SpringDataMemberRepository는 MemberRepository를 상속받고 있기 때문에
스프링 빈에 등록된 객체를 상위 인터페이스인 MemberRepository로 참조할 수 있다.
프록시 패턴을 사용한 기술이다.
 */
public interface SpringDataMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
