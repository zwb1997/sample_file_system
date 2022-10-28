package com.filesystem.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filesystem.consts.ApplicationConsts;

@Configuration
public class AppConfiguration {

    private static final Logger LOG = LogManager.getLogger(AppConfiguration.class);

    @Qualifier("DruidDataSource")
    @Autowired
    private DruidDataSource dataSource;

    private Resource[] mapperLocations;

    @Bean("JacksonMapper")
    public ObjectMapper localJackson() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setConfigurationProperties(getMybatisConfiguration());
        sfb.setFailFast(true);
        sfb.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources(ApplicationConsts.MYBATIS_MAPPER_XML_LOCATION_PATH));

        // this way cannot work well;
        // sfb.setMapperLocations(registerMapperLocation());
        sfb.setDataSource((DataSource) dataSource);
        return sfb.getObject();
    }

    private Properties getMybatisConfiguration() {
        Properties props = new Properties();
        props.setProperty("mapUnderscoreToCamelCase", "true");
        return props;
    }

    // private void registerMapperLocation() {

    // PathMatchingResourcePatternResolver resolver = new
    // PathMatchingResourcePatternResolver();
    // List<Resource> resources = new ArrayList<>();
    // resources.add(resolver.getResource("classpath*:mappers/*Mapper.xml"));
    // this.mapperLocations = resources.toArray(new Resource[resources.size()]);

    // }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(setSqlSessionFactory());
        return sqlSessionTemplate;
    }

    // JDBCtemplate
    @Bean
    public JdbcTemplate createJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    // NamedJdbcTemplate
    @Bean
    public NamedParameterJdbcTemplate createNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
