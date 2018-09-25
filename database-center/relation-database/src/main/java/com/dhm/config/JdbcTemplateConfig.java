package com.dhm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/25 14:25
 */
@Configuration
public class JdbcTemplateConfig {

    @Autowired
    @Qualifier(DataSources.MASTER_DATA_SOURCE)
    DataSource masterDataSource;
    @Autowired
    @Qualifier(DataSources.SLAVE_DATA_SOURCE)
    DataSource slaveDataSource;

    @Bean(name = "masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate() {
        return new JdbcTemplate(masterDataSource);
    }

    @Bean(name = "slaveJdbcTemplate")
    public JdbcTemplate slaveJdbcTemplate() {
        return new JdbcTemplate(slaveDataSource);
    }

}