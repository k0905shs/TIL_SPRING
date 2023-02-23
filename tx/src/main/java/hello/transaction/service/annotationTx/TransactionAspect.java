package hello.transaction.service.annotationTx;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TransactionAspect {

	private static final int READ_ONLY_TIME_OUT = 60;
	private static final int WRITE_TIME_OUT = 60;
	private final static List<String> AOP_TRANSACTION_READONLY_METHOD_NAME = Arrays.asList("get*", "find*", "check*", "exist*", "select*", "list*", "detail*");
	private final static String AOP_TRANSACTION_WRITE_METHOD_NAME = "*";
    private final static String AOP_TRANSACTION_EXPRESSION = "execution(* kr.co.allra..service.*Impl.*(..))";


}
