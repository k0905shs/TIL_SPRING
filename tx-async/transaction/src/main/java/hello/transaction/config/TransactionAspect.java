package hello.transaction.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
public class TransactionAspect {
    private static final int READ_ONLY_TIME_OUT = 50;
    private static final int WRITE_TIME_OUT = 50;
    private final static List<String> AOP_TRANSACTION_READONLY_METHOD_NAME = Arrays.asList("get*", "find*", "check*", "exist*", "select*", "list*", "detail*");
    private final static String AOP_TRANSACTION_WRITE_METHOD_NAME = "*";
    private final static String AOP_TRANSACTION_EXPRESSION = "execution(* hello.transaction.service.v2.*Impl.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        TransactionInterceptor txAdvice = new TransactionInterceptor();

        Properties txAttributes = new Properties();
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<RollbackRuleAttribute>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));
        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_SUPPORTS);
        readOnlyAttribute.setReadOnly(true);
        readOnlyAttribute.setTimeout(READ_ONLY_TIME_OUT);

        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(WRITE_TIME_OUT);

        String readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString();
        String writeTransactionAttributesDefinition = writeAttribute.toString();
        for (String methodPattern : AOP_TRANSACTION_READONLY_METHOD_NAME) {
            txAttributes.setProperty(methodPattern, readOnlyTransactionAttributesDefinition);
        }
        txAttributes.setProperty(AOP_TRANSACTION_WRITE_METHOD_NAME, writeTransactionAttributesDefinition);

        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(transactionManager);
        return txAdvice;
    }

    @Bean
    public Advisor transactionAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
