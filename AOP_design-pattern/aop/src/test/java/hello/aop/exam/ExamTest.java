package hello.aop.exam;

import hello.aop.exam.aop.RetryAspect;
import hello.aop.exam.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Slf4j
@Import({TraceAspect.class, RetryAspect.class})
public class ExamTest {

    @Autowired
    ExamService examService;

    /**
     * [trace] : void hello.aop.exam.ExamService.request(String) [args] : [data0]
     * [trace] : String hello.aop.exam.ExamRepository.save(String) [args] : [data0]
     */
    @Test
    void test(){
        for(int i=0 ; i<5; i++){
            log.info("client request i={}",i );
            examService.request("data"+i);
        }
    }
}
