package com.woowacourse.thankoo.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String SLACK_USERS_CACHE_NAME = "slack-users";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(SLACK_USERS_CACHE_NAME);
    }
}
