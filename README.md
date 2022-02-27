# 스프링
## 스프링 빈
* [configuration 방식 DI](/core/src/main/java/hello/core/AppConfig.java)
* [컴포넌트 스캔 대상 설정](/core/src/main/java/hello/core/AutoAppConfig.java)
* __DI 방식__
  + [생성자 주입](/core/src/main/java/hello/core/member/MemberServiceImpl.java)
  + [수정자 주입](/core/src/main/java/hello/core/order/OrderServiceImpl.java)
  + [필드 주입](/aop/src/test/java/hello/aop/internalcall/CallServiceV1Test.java)
* __같은 타입의 빈이 여러개 있을 때__
  + [@Qualifier1](/core/src/main/java/hello/core/member/MemberServiceImpl.java)
  + [@Qualifier2, @Primary](/core/src/main/java/hello/core/member/MemoryMemberRepository.java)
  + [@Qualifier를 커스텀 어노테이션 으로 만들어서 컴파일시 타입 체크 되도록 하기](/core/src/main/java/hello/core/annotation/MainDiscountPolicy.java)
* [@PostContruct, @PreDestroy](/core/src/test/java/hello/core/lifecycle/NetworkClient.java)
* __빈 스코프__
  + [싱글톤 스코프](/core/src/test/java/hello/core/scope/SingletonTest.java)
  + [프로토타입 스코프](/core/src/test/java/hello/core/scope/PrototypeTest.java)
  + [싱글톤 빈에 프로토타입 빈 주입 받기](/core/src/test/java/hello/core/scope/SingletonWithPrototypeTest1.java)
  + [웹 스코프](/core/src/main/java/hello/core/common/MyLogger.java)
## 스프링 MVC
* __컨트롤러__
  + [URL 매핑(@RequestMapping 등) 및 관련 속성](/springmvc/src/main/java/hello/springmvc/basic/request/RequestParamController.java)
  + [@PathVariable](/springmvc/src/main/java/hello/springmvc/basic/request/RequestParamController.java)
  + [@RequestParam, @ModelAttribute](/springmvc/src/main/java/hello/springmvc/basic/request/RequestParamController.java)
  + [@ModelAtttribute 2](/form/src/main/java/hello/itemservice/web/form/FormItemController.java)
  + [HttpEntity, @RequestBody String으로 받기](/springmvc/src/main/java/hello/springmvc/basic/request/RequestBodyStringController.java)
  + [HttpEntity, @RequestBody JSON으로 받기, JSON 반환](/springmvc/src/main/java/hello/springmvc/basic/request/RequestBodyJsonController.java)
  + [@ControllerAdvice, @RestControllerAdvice](exception/src/main/java/hello/exception/exhandler/advice/ExControllerAdvice.java)
  + [RedirectAttributes, 리다이렉트시 경로변수 주기](/upload/src/main/java/hello/upload/controller/ItemController.java)
* __thymeleaf__
  + [th:text, &#91;&#91;...&#93;&#93;](/thymeleaf-basic/src/main/resources/templates/basic/text-basic.html)
  + [unescape 하기 : th:utext, &#91;(...)&#93;](/thymeleaf-basic/src/main/resources/templates/basic/text-unescaped.html)
  + [${...}, SpEL 표현식 사용, 지역변수 사용](/thymeleaf-basic/src/main/resources/templates/basic/variable.html)
  + [타임리프 기본 객체(#request, #response, #session 등)](/thymeleaf-basic/src/main/resources/templates/basic/basic-objects.html)
  + [날짜 관련 유틸리티 객체](/thymeleaf-basic/src/main/resources/templates/basic/date.html)
  + [th:href](/thymeleaf-basic/src/main/resources/templates/basic/link.html)
  + [리터럴, 리터럴 대체 문법 : |...|사용](/thymeleaf-basic/src/main/resources/templates/basic/literal.html)
  + [연산식, Elvis(?:), no-operation(_)](/thymeleaf-basic/src/main/resources/templates/basic/operation.html)
  + [th:attrappend, th:attrprepend, th:classappend, th:checked](/thymeleaf-basic/src/main/resources/templates/basic/attribute.html)
  + [반복 : th:each](/thymeleaf-basic/src/main/resources/templates/basic/each.html)
  + [조건문 : th:if, th:unless, th:switch, th:case](/thymeleaf-basic/src/main/resources/templates/basic/condition.html)
  + [타임리프 주석](/thymeleaf-basic/src/main/resources/templates/basic/comments.html)
  + [블록으로 묶어서 타임리프 기능 사용 : th:block](/thymeleaf-basic/src/main/resources/templates/basic/block.html)
  + [자바 스크립트 인라인 : th:inline="javascript"](/thymeleaf-basic/src/main/resources/templates/basic/javascript.html)
  + __템플릿 조각 활용__
    - [th:fragment](/thymeleaf-basic/src/main/resources/templates/template/fragment/footer.html)
    - [th:insert, th:replace](/thymeleaf-basic/src/main/resources/templates/template/fragment/fragmentMain.html)
  + __템플릿 레이아웃1(일부 태그만 레이아웃을 활용)__
    - [레이아웃에 태그 전달(~{::tagName}), 실제 페이지 컨텐츠](/thymeleaf-basic/src/main/resources/templates/template/layout/layoutMain.html)
    - [레이아웃](/thymeleaf-basic/src/main/resources/templates/template/layout/base.html)
  + __템플릿 레이아웃2(전체 html 파일에 대해 레이아웃을 활용)__
    - [레이아웃에 태그 전달(~{::tagName}), 실제 페이지 컨텐츠](/thymeleaf-basic/src/main/resources/templates/template/layoutExtend/layoutExtendMain.html)
    - [레이아웃](/thymeleaf-basic/src/main/resources/templates/template/layoutExtend/layoutFile.html)
  + [th:object, th:field, *{...}](/form/src/main/resources/templates/form/addForm.html)
  + [validation에서의 th:field](/validation/src/main/resources/templates/validation/v2/addForm.html)
  + [th:for, ${#ids.prev(...)}, ${#ids.next(...)}, ${#ids.seq(...)}](/form/src/main/resources/templates/form/addForm.html)
* __메세지, 국제화__
  + [messages.properties](/message/src/main/resources/messages.properties)
  + [messages_en.properties](/message/src/main/resources/messages_en.properties)
  + [메세지 테스트 코드, 메세지 적용 순서](/message/src/test/java/hello/itemservice/message/MessageSourceTest.java)
  + [타임리프 적용 : #{...}](/message/src/main/resources/templates/message/item.html)
* __Validation__
  + [Validator 구현](/validation/src/main/java/hello/itemservice/web/validation/ItemValidator.java)
  + [@InitBinder, Validator 추가(init() 메서드 보기)](/validation/src/main/java/hello/itemservice/web/validation/ValidationItemControllerV2.java)
  + [@Validated, BindingResult](/validation/src/main/java/hello/itemservice/web/validation/ValidationItemControllerV4.java)
  + [#fields, th:errors, th:errorclass](/validation/src/main/resources/templates/validation/v4/addForm.html)
  + [검증 에러 메세지 설정](/validation/src/main/resources/errors.properties)
  + [필드 오류, Bean Validation](/validation/src/main/java/hello/itemservice/web/validation/form/ItemSaveForm.java)
  + [글로벌 오류](/validation/src/main/java/hello/itemservice/web/validation/ValidationItemControllerV4.java)
* __필터, 인터셉터__
  + [서블릿 필터](/login/src/main/java/hello/login/web/filter/LogFilter.java)
  + [서블릿 필터 등록](/login/src/main/java/hello/login/WebConfig.java)
  + [스프링 인터셉터](/login/src/main/java/hello/login/web/interceptor/LogInterceptor.java)
  + [스프링 인터셉터 등록](/login/src/main/java/hello/login/WebConfig.java)
* __커스텀 ArgumentResolver 사용하기__
  + [커스텀 어노테이션 생성(꼭 만들 필요는 없음)](/login/src/main/java/hello/login/web/argumentresolver/Login.java)
  + [커스텀 ArgumentResolver](/login/src/main/java/hello/login/web/argumentresolver/LoginMemberArgumentResolver.java)
  + [커스텀 ArgumentResolver 등록](/login/src/main/java/hello/login/WebConfig.java)
* __오류 처리, 오류 페이지__
  + [오류 페이지 요청에 대한 필터, 인터셉터 설정](/exception/src/main/java/hello/exception/WebConfig.java)
  + __뷰에 어떤 오류정보 노출할지 설정__
    - [application.properties](/exception/src/main/resources/application.properties)
    - [뷰](/exception/src/main/resources/templates/error/500.html)
  + [ExceptionResolver 직접 구현](/exception/src/main/java/hello/exception/resolver/MyHandlerExceptionResolver.java)
  + [내가 만든 예외 발생 시 오류코드 설정 : @ResponseStatus](/exception/src/main/java/hello/exception/exception/BadRequestException.java)
  + [예외 발생 시 오류코드 바꾸기, 메세지 바꾸기 : ResponseStatusException](exception/src/main/java/hello/exception/api/ApiExceptionController.java)
  + [API(JSON) 방식 오류 처리 : @ExceptionHandler](exception/src/main/java/hello/exception/exhandler/advice/ExControllerAdvice.java)
  + [예외 처리 메서드 따로 분리하기](exception/src/main/java/hello/exception/exhandler/advice/ExControllerAdvice.java)
* __타입 컨버터__
  + [커스텀 타입 컨버터](/typeconverter/src/main/java/hello/typeconverter/converter/StringToIpPortConverter.java)
  + [커스텀 포맷터](/typeconverter/src/main/java/hello/typeconverter/formatter/MyNumberFormatter.java)
  + [@NumberFormat, @DateTimeFormat](/typeconverter/src/main/java/hello/typeconverter/controller/FormatterController.java)
  + [커스텀 타입 컨버터, 포맷터 추가](/typeconverter/src/main/java/hello/typeconverter/WebConfig.java)
  + __타입 컨버터, 포매터 사용하기__
    - [뷰(thymeleaf)에 타입 컨버터 적용1(출력 시) : ${{...}}](/typeconverter/src/main/resources/templates/converter-view.html)
    - [뷰(thymeleaf)에 타입 컨버터 적용2(입력 시) : th:field](/typeconverter/src/main/resources/templates/converter-form.html)
    - [컨트롤러에서 타입 컨버터 적용](/typeconverter/src/main/java/hello/typeconverter/controller/ConverterController.java)
* __파일 업로드__
  + [업로드할 때 파일명, 저장할 때 파일 명 분리하기](/upload/src/main/java/hello/upload/domain/UploadFile.java)
  + [저장할 객체, MultipartFile 그대로 저장X](/upload/src/main/java/hello/upload/domain/Item.java)
  + [바인딩할 form, MultipartFile 이란](/upload/src/main/java/hello/upload/controller/ItemForm.java)
  + [데이터베이스에 파일 그대로 저장X, 파일이 저장된 경로를 저장](/upload/src/main/java/hello/upload/file/FileStore.java)
  + [업로드 사이즈 제한](/upload/src/main/resources/application.properties)
  + __클라이언트가 서버에 파일 저장__
    - [뷰](/upload/src/main/resources/templates/item-form.html)
    - [파일 저장](/upload/src/main/java/hello/upload/controller/ItemController.java)
  + __클라이언트가 서버에서 파일 다운로드 받기__
    - [뷰, th:src](/upload/src/main/resources/templates/item-view.html)
    - [파일 다운로드 받기(downloadAttach() 보기)](/upload/src/main/java/hello/upload/controller/ItemController.java)
    - [이미지 파일 받기(downloadImage() 보기)](/upload/src/main/java/hello/upload/controller/ItemController.java)
## AOP
* [@Aspect, AOP 만들기, 어드바이저 만들기](/aop/src/main/java/hello/aop/order/aop/AspectV1.java)
* __포인트컷 분리, 모듈화__
  + [모듈화X](/aop/src/main/java/hello/aop/order/aop/AspectV2.java)
  + __모듈화O__
    - [포인트컷](/aop/src/main/java/hello/aop/order/aop/Pointcuts.java)
    - [모듈화한 포인트컷 사용](/aop/src/main/java/hello/aop/order/aop/AspectV4Pointcut.java)
  + [애스펙트 적용 순서](/aop/src/main/java/hello/aop/order/aop/AspectV5Order.java)
  + [어드바이스 종류 : @Around, @Before, @AfterReturning, @AfterThrowing, @After](/aop/src/main/java/hello/aop/order/aop/AspectV6Advice.java)
  + [포인트컷 테스트](/aop/src/test/java/hello/aop/pointcut/ExecutionTest.java)
* __AspectJ 포인트컷 표현식__
  + [execution](/aop/src/test/java/hello/aop/pointcut/ExecutionTest.java)
  + [선언 타입만 사용 : within](/aop/src/test/java/hello/aop/pointcut/WithinTest.java)
  + [파라미터만 사용 : args](/aop/src/test/java/hello/aop/pointcut/ArgsTest.java)
  + __@target, @within, 클래스 타겟 어노테이션 사용__
    - [커스텀 어노테이션](/aop/src/main/java/hello/aop/member/annotation/ClassAop.java)
    - [커스텀 어노테이션 적용 및 @target, @within 사용](/aop/src/test/java/hello/aop/pointcut/AtTargetWithinTest.java)
  + __@annotation, 메서드 타겟 어노테이션 사용__
    - [커스텀 어노테이션](/aop/src/main/java/hello/aop/member/annotation/MethodAop.java)
    - [커스텀 어노테이션 적용](/aop/src/main/java/hello/aop/member/MemberServiceImpl.java)
    - [@anntation 사용](/aop/src/test/java/hello/aop/pointcut/AtAnnotationTest.java)
  + [스프링 빈 이름으로 조인포인트 선정 : bean](/aop/src/test/java/hello/aop/pointcut/BeanTest.java)
  + [조인포인트의 파라미터를 어드바이스에 전달](/aop/src/test/java/hello/aop/pointcut/ParameterTest.java)
  + [this, target](/aop/src/test/java/hello/aop/pointcut/ThisTargetTest.java)
## JPA
* [persist, find, remove, clear, detach, flush, close, 더티 체킹](/ex1-hello-jpa/src/main/java/hellojpa/JpaMain.java)
* [@Entity, @Table](/ex1-hello-jpa/src/main/java/hellojpa/Member.java)
* [@Column](/ex1-hello-jpa/src/main/java/hellojpa/Member.java)
* [@Enumerated](/ex1-hello-jpa/src/main/java/hellojpa/Member.java)
* [@Temporal](/ex1-hello-jpa/src/main/java/hellojpa/Member.java)
* [@Lob](/ex1-hello-jpa/src/main/java/hellojpa/Member.java)
* [@Transient](/ex1-hello-jpa/src/main/java/hellojpa/Member.java)
* [@Id, @GeneratedValue, @SequenceGenerator, @TableGenerator](/ex1-hello-jpa/src/main/java/hellojpa/Member.java)
* [다대일 연관관계 : @ManyToOne, @JoinColumn](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
* [일대다 연관관계 : @OneToMany](/ex1-hello-jpa/src/main/java/hellojpa/Team.java)
* [일대일 연관관계 : @OneToOne](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
* __다대다 연관관계__
  + [엔티티1(Member)](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
  + [연결엔티티, 연결테이블(MemberTable)](/ex1-hello-jpa/src/main/java/hellojpa/MemberProduct.java)
  + [엔티티2(Product)](/ex1-hello-jpa/src/main/java/hellojpa/Product.java)
* __양방향 연관관계__
  + [연관관계 주인](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
  + [mappedBy](/ex1-hello-jpa/src/main/java/hellojpa/Team.java)
* [fetchType : LAZY, EAGER](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
* [LAZY 전략의 N+1문제 해결 : @BatchSize](/jpql/src/main/java/jpql/Team.java)
* __슈퍼타입, 서브타입__
  + [슈퍼타입(Item) : @Inheritance, @DiscriminatorColumn](/ex1-hello-jpa/src/main/java/hellojpa/Item.java)
  + [서브타입(Album) : @DiscriminatorValue](/ex1-hello-jpa/src/main/java/hellojpa/Album.java)
* __BaseEntity, 공통 매핑 정보, @MappedSuperclass__
  + [BaseEntity, @MappedSuperclass](/ex1-hello-jpa/src/main/java/hellojpa/BaseEntity.java)
  + [BaseEntity 상속](/ex1-hello-jpa/src/main/java/hellojpa/Team.java)
* [영속성 전이 : cascade](/ex1-hello-jpa/src/main/java/hellojpa/Parent.java)
* [고아객체 : orphanRemoval](/ex1-hello-jpa/src/main/java/hellojpa/Parent.java)
* __값 타입__
  + [임베디드 타입 : @Embeddable](/ex1-hello-jpa/src/main/java/hellojpa/Address.java)
  + [임베디드 타입 사용 : @Embedded](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
  + [하나의 엔티티에서 같은 임베디드 타입 여러번 사용, @AttributeOverrides](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
  + [값 타입 컬렉션 : @ElementCollection, @CollectionTable](/ex1-hello-jpa/src/main/java/hellojpa/Member2.java)
* __JPQL__
  + [JPQL 결과 받기 : getResultList(), getSingleResult()](/jpql/src/main/java/jpql/JpaMain.java)
  + [파라미터 바인딩 : setParameter()](/jpql/src/main/java/jpql/JpaMain.java)
  + [내부조인, 외부조인, 세타조인](/jpql/src/main/java/jpql/JpaMain.java)
  + [연관관계가 없는 내부조인, 연관관계가 없는 외부조인 : on 절](/jpql/src/main/java/jpql/JpaMain.java)
  + [JPQL에서 타입 표현, 프로젝션, new](/jpql/src/main/java/jpql/JpaMain.java)
  + [페이징 : setFirstResult(), setMaxResult()](/jpql/src/main/java/jpql/JpaMain.java)
  + [조건식 : case, coalesce, nullif](/jpql/src/main/java/jpql/JpaMain.java)
  + [JPQL 기본 함수 : concat, substring, locate 등](/jpql/src/main/java/jpql/JpaMain.java)
  + [경로 탐색](/jpql/src/main/java/jpql/JpaMain.java)
  + [페치조인, 컬렉션 페치 조인, distinct](/jpql/src/main/java/jpql/JpaMain.java)
  + [타입 검사 : type, treat](/jpql/src/main/java/jpql/JpaMain.java)
  + __NamedQuery__
    - [@NamedQueries, @NamedQuery](/jpql/src/main/java/jpql/Member.java)
    - [NamedQuery 사용](/jpql/src/main/java/jpql/JpaMain.java)
  + [벌크성 쿼리](/jpql/src/main/java/jpql/JpaMain.java)
  + __사용자 정의 함수 사용__
    - [사용자 정의 함수 등록](/jpql/src/main/java/dialect/MyH2Dialect.java)
    - [방언으로 등록](/jpql/src/main/resources/META-INF/persistence.xml)
    - [사용자 정의 함수 사용](/jpql/src/main/java/jpql/JpaMain.java)
* [테스트에서 @Transactional, @Rollback](/jpashop/src/test/java/jpabook/jpashop/service/MemberServiceTest.java)
* [@Transactional의 readOnly 속성](/jpashop/src/main/java/jpabook/jpashop/service/MemberService.java)
* [이벤트 어노테이션 : @PrePersist, @PostPersist, @PreUpdate, @PostUpdate](/data-jpa/data-jpa/src/main/java/springdata/datajpa/entity/JpaBaseEntity.java)
## 스프링 데이터 JPA
* [리포지토리 만들기 : JpaRepository<>](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* [리포지토리 메서드 정의, 메서드 반환 타입, @Param(파라미터 바인딩)](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* [NamedQuery : @Query](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* __페이징__
  + [Page<> 반환, @Query의 countQuery 속성](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
  + [Slice<> 반환](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
  + [List<> 반환](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
  + [Page<>, Slice<> 반환시 메서드의 파라미터로 들어가는 Pageable, PageRequest](/data-jpa/data-jpa/src/test/java/springdata/datajpa/repository/MemberRepositoryTest.java)
  + [엔티티를 Page\<DTO>, Slice\<DTO>로 변환 : map()](/data-jpa/data-jpa/src/test/java/springdata/datajpa/repository/MemberRepositoryTest.java)
  + [Page<>, Slice<>의 메서드](/data-jpa/data-jpa/src/test/java/springdata/datajpa/repository/MemberRepositoryTest.java)
  + [url에서 페이징 정보 받기, @PageableDefault](/data-jpa/data-jpa/src/main/java/springdata/datajpa/controller/MemberController.java)
* [벌크성 쿼리 : @Modifying](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* [@EntityGraph(한정적인 페치조인)](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* [@QueryHints](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* [@Lock](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* __메서드 직접 구현__
  + [사용자 정의 인터페이스, 구현할 메서드 정의](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepositoryCustom.java)
  + [사용자 정의 인터페이스 구현, 메서드 직접 구현](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepositoryImpl.java)
  + [구현한 인터페이스를 리포지토리가 상속](/data-jpa/data-jpa/src/main/java/springdata/datajpa/repository/MemberRepository.java)
* __Auditing__
  + [@CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy, @EntityListeners(AuditingEntityListener.class)](/data-jpa/data-jpa/src/main/java/springdata/datajpa/entity/BaseEntity.java)
  + [AuditAware<> 스프링 빈으로 등록, @EnableJpaAuditing](/data-jpa/data-jpa/src/main/java/springdata/datajpa/DataJpaApplication.java)
* [Persistable<>](/data-jpa/data-jpa/src/main/java/springdata/datajpa/entity/Item.java)
## QueryDSL
* [QueryDSL 설정](/querydsl/querydsl/build.gradle)
* [select 쿼리](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [결과 조회 : fetch(), fetchOne(), fetchFirst()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [정렬 : orderBy(), desc(), asc(), nullsLast(), nullsFirst()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [페이징 : offset(), limit()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [집합 함수 : count(), sum(), avg(), max(), min()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [그룹핑 : groupBy(), having()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [기본 조인 : join(), innerJoin(), leftJoin(), rightJoin()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [세타 조인](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [조인 대상 필터링, 연관관계가 없는 외부조인 : on()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [페치 조인 : fetchJoin()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [서브 쿼리 : JPAExpressions](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [case 문 : CaseBuilder(), when(), then(), otherwise()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [상수 더하기 : Expressions.constant()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [문자 붙이기 : concat(), stringValue()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [Tuple](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* __DTO 반환__
  + __자바 빈 프로퍼티 주입 방식, 필드 주입 방식, 생정자 주입 방식__
    - [QueryDSL에 의존X DTO 클래스](/querydsl/querydsl/src/main/java/orm/querydsl/dto/UserDto.java)
    - [Projections.bean(), Projections.fields(), Projections.constructor()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
    - [alias 주기 : as(), ExpressionUtils.as()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
  + __DTO에 대한 Q파일 생성 방식__
    - [QueryDSL에 의존적인 DTO 클래스, @QueryProjection](/querydsl/querydsl/src/main/java/orm/querydsl/dto/MemberDto.java)
    - [Q클래스 사용 : new QMemberDto()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* __동적 쿼리__
  + [BooleanBuilder 사용](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
  + [where절 사용, BooleanExpression 사용](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [벌크성 쿼리 : update(), delete(), execute()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* [SQL function 사용 : Expressions.stringTemplate()](/querydsl/querydsl/src/test/java/orm/querydsl/QuerydslBasicTest.java)
* __스프링 데이터 JPA, QueryDSL 통합__
  + [Repository](/querydsl/querydsl/src/main/java/orm/querydsl/repository/MemberRepository.java)
  + [RepositoryCustom](/querydsl/querydsl/src/main/java/orm/querydsl/repository/MemberRepositoryCustom.java)
  + [RepositoryCustomImpl](/querydsl/querydsl/src/main/java/orm/querydsl/repository/MemberRepositoryCustomImpl.java)
  + [QueryDSL에서 페이징 : PageableExecutionUtils.getPage(), SliceImpl<>](/querydsl/querydsl/src/main/java/orm/querydsl/repository/MemberRepositoryCustomImpl.java)
# 잡다
## Collection
## Stack
* .push(인자)로 스택 추가
* .pop()으로 스택에서 하나 제거
* .clear()로 전체 스택 초기화
* .peek()으로 가장 상단의 값 반환
* .empty()로 스택이 비어있으면 true 반환
* .contains(인수)로 스택에 인수가 있는지 체크하고 있으면 true 반환
* .size()는 Stack의 크기 출력
## Queue
* .remove()는 c++에서 .pop()과 같다.
* .add(인수)는 c++에서 .push(인수)와 같다
* .peek()는 c++에서 .front()와 같다.
* .poll()은 큐에서 하나를 remove()하고 그 값을 반환한다.
* 큐를 만들 때는 new LinkedList<>()로 만들어야한다.
* empty(), contains(인수), size()는 Stack과 같다.
## Priority Queue
* 처음 Priority Queue를 생성할때 new PriorityQueue<>()로 생성하면 우선순위가 낮은 숫자부터 pop되고(오름차순) new PriorityQueue<>(Collections.reverseOrder())로 하면 반대이다.
* 함수는 Queue의 함수와 같다.(add, remove, poll, peek 등)
## 배열
* Arrays.stream(배열)로 배열을 stream으로 만들 수 있다.
* stream을 .toArray()를 이용하여 배열로 만들 때 기본타입 배열로 만들경우에는 매개변수를 안넣어줘도 되지만 객체나 wrapper 클래스인 배열을 만들 경우에는 .toArray(Integer::new)처럼 매개변수를 넣어줘야한다.
* Arrays.copyOfRange(배열, 시작인덱스, 끝인덱스)로 배열을 원하는 범위만큼 복사할 수 있다.
## List
* .add(value)로 c++에서 push_back(value)와 같은 효과를 얻을 수 있고, .add(index, value)로 해당 인덱스에 value값을 넣을 수 있다.
* .get(index)로 해당 index에 위치한 value값을 얻을 수 있다.
* .remove(index)로 해당 index에 위치한 value를 삭제하고 삭제된 value를 반환한다.
## map
* get(key)로 해당 key에  대응하는 value 객체를 반환
* map을 순회하려면 collection이나 set으로 변환해서 해야함 => .entrySet(), .keySet(), .values()
* .entrySet()은 Map.Entry객체로 반환한다.
* values()를 사용하면 map의 value로 이루어진 collection 객체가 반환됨
* containsKey(key), containsValue(value)로 해당 key나 value와 일치하는 객체가 있는지 확인하고 boolean으로 반환
* Map.Entry 인터페이스로 key와 value를 동시에 다룰 수 있음(getKey(), getValue(), setValue(value)
## stream
* IntStream, LongStream 등에서 .range(1, 100)을 사용하면 해당 자료형에 맞는(int, long) 1~99까지의 값들이 들어있는 Intstream이 생성된다.
* 일반 stream으로 사용하려면(stream<Integer>, stream<Long> 등) .boxed()를 사용해 박싱을 해줘야한다.(int->Integer, long->Long)
* .map(람다식)은 stream의 데이터(객체)들을 람다식에 맞추어 변형해준다. stream객체가 반환된다(IntSteam이면 IntStream, DoubleStream이면 DoubleStream 등 자기자신과 같은 타입으로 반환) mapToInt(람다식)은 IntStream으로, mapToDouble(람다식)은 DoubleStream으로 반환한다.(다른 것도 이와 같은 원리 단, mapToObj(람다식)은 각 요소가 객체인 Stream으로 반환한다.)
* .collect(Collectors 메소드)는 Collectors 메소드를 사용할 수 있도록 한다. Collectors 메소드에는 .toList(), .toSet() .toMap(key, value), .toCollection() 등 원하는 Collection 객체로 만드는 함수가 있다. 그리고 Collectors.joining() 처럼 문자열을 이어붙이는 함수 등 다른 함수도 많다.(즐겨찾기) 배열을 얻고 싶다면 .collect함수를 없이 stream.toArray()를 쓰면 배열로 변환되어 반환된다.
* .sorted(람다식)은 람다식을 comparator의 compare함수와 같이 이용해서 stream을 정렬할 수 있다. 일반적인 경우처럼 comparator 객체를 넘겨줘도 된다.
* .groupingBy(그룹화할 값(람다식), Collectors 메소드)은 해당 스트림을 그룹화할 값으로 그룹화해 Map으로 반환한다.(그룹화할 값이 Map의 key가 된다) 2번째 인수를 안넣으면 default로 value가 List의 형태로 저장되고 .groupingBy(그룹화할 값(람다식), toSet()) 처럼 두번째 인수를 Collectors 메소드를 넣어주면 그 함수가 반환하는 객체로 value가 저장된다.
* .flatMap(람다식)은 stream이 List, Set, Map 등으로 이루어져 있을때 사용한다. 만약 flatMap 대신 Map을 사용한다면 List, Set, Map 내의 요소에 대한 filter()등을 적용하지 못한다.(즐겨찾기)
* .limit(int)를 사용하여 stream생성시 stream의 길이에 제한을 둘 수 있다.
* stream을 .toArray()를 이용하여 배열로 만들 때 기본타입 배열로 만들경우에는 매개변수를 안넣어줘도 되지만 객체나 wrapper 클래스인 배열을 만들 경우에는 .toArray(Integer::new)처럼 매개변수를 넣어줘야한다.
* forEach() : map()은 스트림의 값 자체를 바꾸지만 forEach()는 스트림의 값 자체를 변경하는게 아니라 값을 꺼내서 그 값으로 작업을 할 때 사용한다.
* filter() : filter(람다식)의 람다식이 참이면 해당 값을 필터링한다.
## Optional
* .get()메소드를 통해 Optional객체에 저장된 값에 접근할 수 있다.
* .orElse(인수)는 Optional객체가 null이 아니면 그 값을 받고 null이면 orElse안의 인수를 받는다.
* .orElseGet(람다식)은 Optional객체가 null이 아니면 그 값을 받고 null이면 람다식의 결과값을 받는다.
* .orElseThrow(Exception)은 Optional객체가 null이 아니면 그 값을 받고 null이면 orElseThrow안의 예외를 발생시킨다.
* .ofNullable()함수는 안의 매개변수를 Optional 객체로 감싼다. .of()함수도 Optional 객체로 감싸주지만 안의 매개변수가 null이면 안된다.
* .ifPresent(람다식)함수는 Optional에 값이 있으면 람다식을 실행한다.
## 기타
* pair를 사용려면 직접 정의해서 사용해야함
* ::은 람다식을 더 축약한 것이다. 메소드 참조라고 한다. ex)  e->Collection.stream(e) => Collection::stream
* 문자열을 숫자로 변환할때는 Integer.valueOf(문자열), Integer.parseInt(문자열)을 사용하면 된다. 정석적으로는 parseInt가 정석이지만 성능이나 기능상으로 똑같으니깐 어떤 것을 쓰든 상관x
* PatternMatchUtils, StringUtils, UriUtils 등 유용한 유틸리티 객체 있음
* 함수 내부호출 할 때 앞쪽에 this가 생략된 거임
* [커스텀 어노테이션](core/src/main/java/hello/core/annotation/MainDiscountPolicy.java)
* [로그 찍기](/springmvc/src/main/java/hello/springmvc/basic/LogTestController.java)
* [enum 타입 discription 추가해서 정의하기](/form/src/main/java/hello/itemservice/domain/item/ItemType.java)
* [쿠키 생성 및 사용(loginV1, logoutV1 보기)](/login/src/main/java/hello/login/web/login/LoginController.java)
* __세션 생성 및 사용__
  + [세션1(loginV4, logoutV3 보기)](/login/src/main/java/hello/login/web/login/LoginController.java)
  + [세션2](/login/src/main/java/hello/login/web/session/SessionInfoController.java)
* [Provider, ObjectProvider](/core/src/test/java/hello/core/scope/SingletonWithPrototypeTest1.java)
* [테스트, mock 요청, 응답 : MockHttpServletRequest, MockHttpServletResponse](/login/src/test/java/hello/login/web/session/SessionManagerTest.java)