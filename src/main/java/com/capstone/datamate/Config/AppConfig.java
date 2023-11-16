package com.capstone.datamate.Config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.capstone.datamate")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://dpg-clacpn9m6hds73dhnba0-a.oregon-postgres.render.com/db_datamate");
        dataSource.setUsername("db_datamate_user");
        dataSource.setPassword("Oj9iHI28hYp5nEcmwwvouxw6bTaIdFZA");

        return dataSource;
    }

    @Bean
    public JdbcTemplate getjdbctemplate() {
    return new JdbcTemplate(dataSource());
    }
}
