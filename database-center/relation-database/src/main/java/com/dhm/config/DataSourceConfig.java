package com.dhm.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/15 14:07
 * <p>
 * 主从数据源配置
 */
@Configuration
public class DataSourceConfig {

    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
    @Bean(destroyMethod = "close", name = DataSources.MASTER_DATA_SOURCE)
    @Qualifier(DataSources.MASTER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @Primary
    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().build();
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
    @Bean(destroyMethod = "close", name = DataSources.SLAVE_DATA_SOURCE)
    @Qualifier(DataSources.SLAVE_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
//        return DataSourceBuilder.create().build();
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

}