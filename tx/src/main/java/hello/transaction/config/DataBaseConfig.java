package hello.transaction.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement// 트랜젝션 처리를 허용해주는 어노테이션
public class DataBaseConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean sqlSessionFactory  = new SqlSessionFactoryBean();
//		sqlSessionFactory.setDataSource(dataSource);
//		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/**/*.xml"));
//		//sqlSessionFactory.setTypeAliases(typeAliases);
//		return sqlSessionFactory.getObject();
		return null;
	}


	/**
	 * 트랜잭션 매니저 설정
	 */
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
