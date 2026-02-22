package com.github.dev_jakki.library_api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // Esse não é recomendado para aplicações de larga escala, pode tratar
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//
//        ds.setUrl(url);
//        ds.setUsername(username);
//        ds.setPassword(password);
//        ds.setDriverClassName(driver);
//
//        return ds;
//    }

    // Recomendado user Hikari
    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); // Máximo de conexões liberadas
        config.setMinimumIdle(1); // Tamanho inicial do pool
        config.setPoolName("library-hikari-pool");
        config.setMaxLifetime(600000); // 600 mil ms (10 min)
        config.setConnectionTimeout(100000); // Timeout para conseguir uma conexão
        config.setConnectionTestQuery("SELECT 1"); // Query de teste

        return new HikariDataSource(config);
    }
}
