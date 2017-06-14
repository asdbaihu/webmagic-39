package com.mydemo.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by admin on 2017/6/12.
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig {


    private final static Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Bean(name = "dataSource")
    @Qualifier(value = "dataSource")
    @Primary
    public DataSource dataSource()
    {

        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl("jdbc:mysql://localhost:3306/mytest?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=UTC&useSSL=false");
        datasource.setUsername("root");
        datasource.setPassword("root");
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        //configuration
        datasource.setInitialSize(5);
        datasource.setMinIdle(5);
        datasource.setMaxActive(20);
        datasource.setMaxWait(60000);
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        datasource.setMinEvictableIdleTimeMillis(300000);
        datasource.setValidationQuery("SELECT 1 FROM DUAL");
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setPoolPreparedStatements(true);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        try {
            datasource.setFilters("stat,wall,log4j");
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");

        return datasource;
    }

    @Bean(name ="sqlSessionFactory")
    @Qualifier(value = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        logger.debug("--> sqlSessionFactory");
        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        sqlSessionFactory.setFailFast(true);
        sqlSessionFactory.setTypeAliasesPackage("com.mydemo.domain");
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*Mapper.xml"));//每张表对应的xml文件
            return sqlSessionFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean(name ="dataSourceTransactionManager")
    @Qualifier(value = "dataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager() {
        logger.debug("> transactionManager");
        return new DataSourceTransactionManager(dataSource());
    }


    /*@PostConstruct
    public void postConstruct() {
        logger.info("---------------------jdbc.url{}", "jdbc:mysql://localhost:3306/mytest?useUnicode = true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
        logger.info("---------------------jdbc.username{}", "root");
        logger.info("---------------------jdbc.password{}", "root");
        logger.info("---------------------jdbc.driver{}","com.mysql.jdbc.Driver");
    }*/


    @Bean(name ="sqlSessionTemplate")
    @Qualifier(value = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name ="platformTransactionManager")
    @Qualifier(value = "platformTransactionManager")
    @Primary
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}
