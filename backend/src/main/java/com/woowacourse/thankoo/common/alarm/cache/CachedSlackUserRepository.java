package com.woowacourse.thankoo.common.alarm.cache;

import com.woowacourse.thankoo.common.alarm.SlackClient;
import com.woowacourse.thankoo.common.alarm.dto.SlackUserResponse;
import com.woowacourse.thankoo.common.alarm.dto.SlackUsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CachedSlackUserRepository {

    private final SlackClient slackClient;

    @Cacheable(cacheNames = "slack", key = "#email")
    public CachedSlackUser getCachedSlackUser(final String email) {
        SlackUsersResponse slackUsers = slackClient.getSlackUsers();
        SlackUserResponse user = slackClient.findUser(email, slackUsers);
        return new CachedSlackUser(user.getProfile().getEmail(), user.getId());
    }
}
