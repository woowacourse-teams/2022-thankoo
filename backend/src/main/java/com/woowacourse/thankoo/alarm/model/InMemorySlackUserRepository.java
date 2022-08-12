package com.woowacourse.thankoo.alarm.model;

import com.woowacourse.thankoo.alarm.infrastructure.SlackClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemorySlackUserRepository {

    private final Map<String, String> store;
    private final SlackClient slackClient;

    public String findUserId(final String email) {
        return store.computeIfAbsent(email, slackClient::getUserToken);
    }
}
