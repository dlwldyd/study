package springdata.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

//AuditorAware<>를 스프링 빈으로 등록시 필수
@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	//@CreatedBy, @LastModifiedBy 가 붙은 객체에 무엇을 반환할지
	@Bean
	public AuditorAware<String> auditorProvider() {
		//스프링 시큐리티 사용시 SecurityContextHolder.getContext().getAuthentication().getPrincipal().getUsername()을 반환할 듯?
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
