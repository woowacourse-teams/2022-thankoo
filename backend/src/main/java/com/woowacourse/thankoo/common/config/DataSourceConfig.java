package com.woowacourse.thankoo.common.config;

import com.woowacourse.thankoo.common.datasource.DataSourceType;
import com.woowacourse.thankoo.common.datasource.ReplicationRoutingDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Profile("dev")
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @DependsOn("targetDataSource")
    public DataSource dataSource(@Qualifier("targetDataSource") final DataSource targetDataSource) {
        return new LazyConnectionDataSourceProxy(targetDataSource);
    }

    @Bean
    @DependsOn({"readDataSource", "writeDataSource"})
    public DataSource targetDataSource(@Qualifier("readDataSource") final DataSource readDataSource,
                                       @Qualifier("writeDataSource") final DataSource writeDataSource) {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.READ, readDataSource);
        dataSources.put(DataSourceType.WRITE, writeDataSource);

        ReplicationRoutingDataSource dataSource = new ReplicationRoutingDataSource();
        dataSource.setTargetDataSources(dataSources);
        dataSource.setDefaultTargetDataSource(writeDataSource);
        return dataSource;
    }
}
