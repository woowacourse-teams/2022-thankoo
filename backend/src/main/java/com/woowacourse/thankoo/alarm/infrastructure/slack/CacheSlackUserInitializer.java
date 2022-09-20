package com.woowacourse.thankoo.alarm.infrastructure.slack;

import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUserResponse;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUsersResponse;
import com.woowacourse.thankoo.common.config.CacheConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"prod", "dev", "local"})
@Component
@RequiredArgsConstructor
public class CacheSlackUserInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final CacheManager cacheManager;
    private final SlackUsersClient slackUsersClient;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        SlackUsersResponse users = slackUsersClient.getUsers();
        Cache cache = cacheManager.getCache(CacheConfig.SLACK_USERS_CACHE_NAME);
        if (cache != null) {
            users.getResponses()
                    .stream()
                    .filter(SlackUserResponse::isEmailExist)
                    .forEach(user -> cache.put(user.getProfile().getEmail(), user.getUserToken()));
        }
    }
}
