package hello.toy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:config/db.properties"),
//        @PropertySource("classpath:config/db_login.properties"),
        @PropertySource("classpath:config/mybatis-config.properties")
})
public class Config {
}
