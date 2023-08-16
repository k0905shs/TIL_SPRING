package hello.proxy;

import hello.proxy.trace.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Import : 클래스를 스프링 빈에 등록한다
 * 일반적으로 Configuration 같은 설정파일을 등록할때 사용하지만, 스프링 빈을 등록할 때도 사용할 수 있다.
 *
 * @SpringBootApplication : @ComponentScan의 기능과 같다. 컴포넌트 스캔을 시작할 위치를 지정한다.
 * */
@Import(AppV2Config.class)
//@Import(AppV1Config.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
