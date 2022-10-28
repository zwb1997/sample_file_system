package com.filesystem.config;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;

@Configuration
// @PropertySource("classpath:/db/db.properties")
public class CustomDataSourceConfiguration {

    private static final Logger LOG = LogManager.getLogger(CustomDataSourceConfiguration.class);

    @Autowired
    private Environment env;

    @Value("${db.url}")
    public String db_url;

    @Value("${db.u}")
    public String db_user;

    @Value("${db.p}")
    public String db_secret;

    @Value("${db.driver}")
    public String db_driver;

    @Bean(name = "DruidDataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource druidDataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(db_url);
        dataSource.setUsername(db_user);
        dataSource.setPassword(db_secret);
        dataSource.setDriverClassName(db_driver);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
        dataSource.setMaxWait(6000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(false);
        dataSource.setMaxOpenPreparedStatements(20);
        dataSource.setValidationQuery("SELECT 1");

        Log4j2Filter log4j2Filter = new Log4j2Filter();
        // dataSource.setFilters("log4j2");
        log4j2Filter.setStatementExecutableSqlLogEnable(true);
        log4j2Filter.setStatementCreateAfterLogEnabled(false);
        log4j2Filter.setStatementPrepareAfterLogEnabled(false);
        log4j2Filter.setStatementExecuteAfterLogEnabled(false);
        log4j2Filter.setStatementSqlPrettyFormat(false);
        dataSource.setProxyFilters(List.of(log4j2Filter));

        dataSource.setAsyncInit(true);
        return dataSource;
    }
}
