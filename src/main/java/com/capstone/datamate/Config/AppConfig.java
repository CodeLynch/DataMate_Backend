package com.capstone.datamate.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.capstone.datamate")
public class AppConfig {
 
    @Value("${DB_USERNAME}")
    private String user;
    
    @Value("${DB_PASSWORD}")
    private String pass;
        
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://dpg-clacpn9m6hds73dhnba0-a.oregon-postgres.render.com/db_datamate");
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        return dataSource;
    }

    @Bean
    public JdbcTemplate getjdbctemplate() {
    return new JdbcTemplate(dataSource());
    }
}
