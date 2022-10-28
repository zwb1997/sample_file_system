package com.filesystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = { "com.filesystem" })
@PropertySource("classpath:db/db.properties")
@MapperScan(basePackages = { "com.filesystem.mapper" })
public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        LOG.info("service start");
    }

}
