## 3-1. AOP 로그 추적기 (AOP_LOG_TRACER)
#### (hello.toy.logTrace.정리.txt)    
#### 위치 : hello.toy.logTrace  
#### Spring AOP를 활용해서 만든 Log 추적기호출기  
#### 각각의 Layer Level을 표현을 구현하기 위해 ThreadLocal을 사용해서 각 Thread마다 쓰레드 지역 변수를 가질 수 있도록 했다.  
#### 로그 추적 결과 :
[ID]  level 이동 그림,  메소드 위치, level, (예외 발생시) 예외 정보  
#### (정상 호출)
<img src="https://i.ibb.co/qsF5D5P/image.png" alt="image" border="0" width="700" height="200">  

[e3991604]   --> , location : String hello.toy.LogTrace.LogTraceController.tt(), level : 1  
[e3991604]  |   | --> , location : String hello.toy.LogTrace.TestService.t1(), level : 2  
[e3991604]  |   ||   | --> , location : String hello.toy.LogTrace.TestService.t2(), level : 3  
[e3991604]  |   ||   ||   | --> , location : String hello.toy.LogTrace.TestRepository.t3(), level : 4  
[e3991604]  |   ||   ||   | <-- , location : String hello.toy.LogTrace.TestRepository.t3(), level : 4  
[e3991604]  |   ||   | <-- , location : String hello.toy.LogTrace.TestService.t2(), level : 3  
[e3991604]  |   | <-- , location : String hello.toy.LogTrace.TestService.t1(), level : 2  
[e3991604]   <-- , location : String hello.toy.LogTrace.LogTraceController.tt(), level : 1  

#### (예외 발생)
<img src="https://i.ibb.co/0GC2htz/image.png" alt="image" border="0" width="700" height="160">  

[a2339553]   --> , location : String hello.toy.LogTrace.LogTraceController.tt(), level : 1  
[a2339553]  |   | --> , location : String hello.toy.LogTrace.TestService.t1(), level : 2  
[a2339553]  |   ||   | --> , location : String hello.toy.LogTrace.TestService.t2(), level : 3  
[a2339553]  |   ||   | x-- , location : String hello.toy.LogTrace.TestService.t2(), level : 3, Exception : java.lang.NullPointerException  
[a2339553]  |   | x-- , location : String hello.toy.LogTrace.TestService.t1(), level : 2, Exception : java.lang.NullPointerException  
[a2339553]   x-- , location : String hello.toy.LogTrace.LogTraceController.tt(), level : 1, Exception : java.lang.NullPointerException  
2022-01-26 16:48:16.299 ERROR 18116 --- [nio-8080-exec-9] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.NullPointerException] with root cause
