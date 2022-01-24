# 인텔리제이 단축키
* Alt+insert : 함수 자동 생성
* Ctrl+p : 파라미터 정보 보기(어떤 파라미터를 넣어야하는 지 알 수 있음)
* Ctrl+Shift+Enter : 마지막에 세미콜론찍고 해당줄의 끝으로 감, 세미콜론이 찍혀있는데 쓰면 아래줄로 내려감
* Shift+F6 : 변수, 함수이름 한꺼번에 rename
* Ctrl+Alt+v : 변수 자동생성
* Ctrl+Alt+Shift+t : 리팩토링과 관련된 메뉴가 뜬다.
* Ctrl+Shift+t : 테스트 하고싶은 클래스에서 Ctrl+Shift+t 를 하면 자동으로 패키지와 파일이 만들어진다.
* shift+f10 : 이전에 실행했던 코드를 다시 샐행함
* Ctrl+e : 최근에 봤던 파일 목록을 확인할 수 있다. 바로 엔터하면 바로 이전에 봤던 파일로 돌아갈 수 있다.
* Shift+Tab : Tab(들여쓰기)의 반대
* psvm : 에디터에 psvm이라 치면 자동으로 public static void main(String args[]) 만들어줌
* Ctrl+Alt+M : extract method 리팩토링 단축키
* iter : 에디터에 iter이라 치고 Tab누르면 for문 자동 완성
* soutv : 에디터에 soutv이라 치면 자동으로 System.out.println() 만들어줌(변수 출력)
* soutm : 에디터에 soutm이라 치면 자동으로 System.out.println() 만들어줌(메소드로 출력)
* Shift 2번 : search
* Ctrl+f12 : 클래스 구성 볼 수 있음
* Ctrl+O : 오버라이드할 함수 목록 볼 수 있음
* Ctrl+Alt+C : 상수 생성(상수로 뺌)
* Alt+1 : 프로젝트 탭으로 넘어갈 수 있음
* 프로젝트 탭에서 ESC : 코드 탭으로 넘어갈 수 있음
* Ctrl+Alt+B : 함수를 구현한 클래스로 이동, 객체를 생성한 클래스로 이동, 인터페이스를 구현한 클래스로 이동
* /** 하고 엔터 : javadocs 생성
* List이름.iter : List에 대한 for문 자동 완성
* Ctrl+Shift+F : 파일, 디렉토리 내에서 문자열 찾기
* Ctrl+Shift+R : 파일, 디렉토리 내에서 문자열 찾아서 대체
* Ctrl+R : 문자열 찾아서 대체
* Ctrl+Alt+N : 인라인 한줄로 만들기
* f2 : 오류 있는 곳으로 바로 이동
# 자바
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
* .groupingBy(그룹화할 값, Collectors 메소드)은 해당 스트림을 그룹화할 값으로 그룹화해 Map으로 반환한다. 2번째 인수를 안넣으면 default로 value가 List의 형태로 저장되고 .groupingBy(그룹화할 값, toSet()) 처럼 두번째 인수를 Collectors 메소드를 넣어주면 그 함수가 반환하는 객체로 value가 저장된다.
* .flatMap(람다식)은 stream이 List, Set, Map 등으로 이루어져 있을때 사용한다. 만약 flatMap 대신 Map을 사용한다면 List, Set, Map 내의 요소에 대한 filter()등을 적용하지 못한다.(즐겨찾기)
* .limit(int)를 사용하여 stream생성시 stream의 길이에 제한을 둘 수 있다.
* stream을 .toArray()를 이용하여 배열로 만들 때 기본타입 배열로 만들경우에는 매개변수를 안넣어줘도 되지만 객체나 wrapper 클래스인 배열을 만들 경우에는 .toArray(Integer::new)처럼 매개변수를 넣어줘야한다.
* forEach(), filter(), ifPresent()
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
