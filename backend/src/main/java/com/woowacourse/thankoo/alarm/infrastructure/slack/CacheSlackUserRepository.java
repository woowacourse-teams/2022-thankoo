package com.woowacourse.thankoo.alarm.infrastructure.slack;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheSlackUserRepository {

    private final SlackUsersClient slackUsersClient;

    @Cacheable("slack-users")
    public String getTokenByEmail(final String email) {
        return slackUsersClient.getUserTokenByEmail(email);
    }
}
