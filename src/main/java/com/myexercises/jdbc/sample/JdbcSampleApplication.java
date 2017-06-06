/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myexercises.jdbc.sample;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Acer
 */
@SpringBootApplication
@EnableTransactionManagement
@PropertySource("classpath:/com/myexercises/jdbc/sample/config/application.properties")
public class JdbcSampleApplication {
    @Value("${com.myexercises.jdbc.sample.JdbcSampleApplication.jdbcUrl}")
    private String jdbcUrl;
    @Value("${com.myexercises.jdbc.sample.JdbcSampleApplication.user}")
    private String user;
    @Value("${com.myexercises.jdbc.sample.JdbcSampleApplication.password}")
    private String password;
    @Value("${com.myexercises.jdbc.sample.JdbcSampleApplication.driverClassName}")
    private String driverClassName;
    @Value("${com.myexercises.jdbc.sample.JdbcSampleApplication.maxPoolSize}")
    private Integer maxPoolSize;
    
    @Bean
    DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setMaximumPoolSize(maxPoolSize);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(JdbcSampleApplication.class, args);
    }
}
