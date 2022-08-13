package com.woowacourse.thankoo.alarm.infrastructure;

import com.woowacourse.thankoo.common.exception.ErrorType;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemorySlackUserRepository {

    private final Map<String, String> store;
    private final SlackClient slackClient;

    public String findUserId(final String email) {
        String userId = store.computeIfAbsent(email, slackClient::getUserToken);
        if (userId == null) {
            throw new RuntimeException(ErrorType.NOT_FOUND_SLACK_USER.getMessage());
        }
        return userId;
    }
}
