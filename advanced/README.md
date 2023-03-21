# ThreadLocal  
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
