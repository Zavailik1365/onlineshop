package com.onlineshop.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.onlineshop")
public class DatabaseConfiguration {
    @Value("${jdbc.driver}")
    private String driverClassName;
    @Value("${jdbc.databaseUrl}")
    private String databaseUrl;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(driverClassName);
        dataSourceConfig.setJdbcUrl(databaseUrl);
        dataSourceConfig.setUsername(userName);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setMaximumPoolSize(20);
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
