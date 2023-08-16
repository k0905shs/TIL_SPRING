package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    /**
     * 이걸 테스트를 돌려서 나오는  public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
     * 이게 execution pointcut임
     */
    @Test
    void printMethod(){
        log.info("helloMethod = {}",helloMethod);
    }

    /**
     * 매칭조건이 완전 일치하는 예제
     */
    @Test
    void exactMatch(){
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    /**
     * 가장 많이 생략한 포인트컷
     */
    @Test
    void allMatch(){
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch(){
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1s(){
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }
 @Test
    void nameMatchStar2s(){
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    /**
     * 패키지 매칭
     */
    @Test
    void packageExactMatch(){
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2(){
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    /**
     * 패키지 매치 실패 케이스
     */
    @Test
    void packageExactMatch3(){
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    /**
     * 패키지 매치 성공 케이스\
     * .. 이게 하위 패키지 까지 매치한다는 뜻
     */
    @Test
    void packageExactMatch4녀Sub(){
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }


    /**
     * 타입 매칭 - 부모 타입 허용
     */

    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }
    @Test
    void typeMatchSuperType() {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }


    /**
     typeMatchInternal() 의 경우 MemberServiceImpl 를 표현식에 선언했기 때문에 그 안에 있는
     internal(String) 메서드도 매칭 대상이 된다
     */
    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal",
                String.class);
        assertThat(pointcut.matches(internalMethod,
                MemberServiceImpl.class)).isTrue();
    }
    //포인트컷으로 지정한 MemberService 는 internal 이라는 이름의 메서드가 없다.
    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal",
                String.class);
        assertThat(pointcut.matches(internalMethod,
                MemberServiceImpl.class)).isFalse();
    }

    /**
     * 파라미터 매칭
     */

    //String 타입의 파라미터 허용
    //(String)
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }
    //파라미터가 없어야 함
    //()
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isFalse();
    }


    /**
     정확히 하나의 파라미터 허용, 모든 타입 허용
     (Xxx)
     */
    @Test
    void argsMatchStar() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }

    /**
     * 숫자와 무관하게 모든 파라미터, 모든 타입 허용
     * 파라미터가 없어도 됨
     * (), (Xxx), (Xxx, Xxx)
     */
    @Test
    void argsMatchAll() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();

    }

    /**
     String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용1
     (String), (String, Xxx), (String, Xxx, Xxx) 허용
     */
    @Test
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }


}
