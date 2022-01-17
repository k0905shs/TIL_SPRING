
# 스프링 고급  
  - ThreadLocal
  - TemplateMethod Pattern
  - Strategy Pattern
  - CallbackMethod Pattern

# 1.ThreadLocal  
### 예제  
hello.advanced.v3  

  ### 설명  
  해당 쓰레드만 접근할 수 있는 특별한 저장소를 말한다.  
  자바는 언어 차원에서 ThreadLoal을 지원하기 위한 java.lang.ThreadLocal을 제공한다.  
    
  ### 사용법
  private ThreadLocal<String> nameStore = new ThreadLocal<>();  
  값 저장: ThreadLocal.set(xxx)  
  값 조회: ThreadLocal.get()  
  값 제거: ThreadLocal.remove()  
  
  ### 주의 사항  
해당 쓰레드가 쓰레드 로컬을 모두 사용하고 나면 ThreadLocal.remove() 를 호출하여 쓰레드 로컬에 저장된 값을 제거해주어야 한다.  
쓰레드 로컬의 값을 사용 후 제거하지 않고 그냥 두면 WAS 처럼 쓰레드 풀을 사용하는 경우 심각한 문제를 발생 할 수 있다.  
  
시나리오 ex)
사용자 A 저장 요청
>  사용자 A가 저장 HTTP를 요청했다. -> WAS는 Thread pool에서 Thread를 하나 조회한다. -> ThreadA 할당되었다.  -> ThreadA는 사용자A의 데이터를 ThreadLocal에 저장한다. -> ThreadLocal의 ThreadA 전용 저장소에 사용자 A의 데이터를 보관한다.  
  
사용자A 저장 요청 종료
  > 사용자A의 HTTP 응답이 끝난다. -> WAS는 사용이 끝난 ThreadA를 Thread pool에 반환한다. (Thread를 생성하는 비용은 비싸기 때문에 보통 Thread를 제거하지 안고
                풀을 통해 재사용한다.) -> ThreadA는 Thread pool에 아직 살아있다. 따라서 ThreadLocal의 ThreadA 전용 보관소에 사용자 A의 데이터도 함께 살아있게 된다.

이 후 A를 제외한 사용자 조회 요청
>   HTTP 요청 -> WAS Thread pool 에서 Thread 조회 -> ThreadA 할당 -> ThreadA ThreadLocal 데이터 조회
                -> ThreadLocal ThreadA 보관소에 있는 사용자A 값 반환

위와 같은 문제를 야기 할 수 있으므로, 쓰레드 로컬은 사용이 종료되면 꼬오오옥 remove() 호출해 줘야한다.  

  
# 2.TemplateMethod Pattern 
### 예제  
hello.advanced.v4  
test.java.hello.advanced.trace.hellotrace.template  

  ### 설명  
부모 클래스에 알고리즘의 골격인 템플릿을 정의하고, 일부 변경되는 로직은 자식 클래스에 정의하는  
것이다.   
이렇게 하면 자식 클래스가 알고리즘의 전체 구조를 변경하지 않고, 특정 부분만 재정의할 수 있다.    
결국 상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것이다.

### 문제 
 템플릿 메서드 패턴은 상속을 사용한다. 따라서 상속에서 오는 단점들을 그대로 안고간다. 특히 자식  
 클래스가 부모 클래스와 컴파일 시점에 강하게 결합되는 문제가 있다. 이것은 의존관계에 대한 문제이다.  
 자식 클래스 입장에서는 부모 클래스의 기능을 전혀 사용하지 않는다. 
 
 자식 클래스 입장에서는 부모 클래스의 기능을 전혀 사용하지 않는데, 부모 클래스를 알아야한다. 이것은  
 좋은 설계가 아니다. 그리고 이런 잘못된 의존관계 때문에 부모 클래스를 수정하면, 자식 클래스에도 영향을  
 줄 수 있다.  
      
 상속을 받는 다는 것은 특정 부모 클래스를 의존하고 있다는 것이다. 자식 클래스의 extends 다음에 바로  
 부모 클래스가 코드상에 지정되어 있다.  
   
  따라서 부모 클래스의 기능을 사용하든 사용하지 않든 간에 부모 클래스를 강하게 의존하게 된다.  
  
  
-해결 방안  
 전략 패턴(Strategy Pattern)
