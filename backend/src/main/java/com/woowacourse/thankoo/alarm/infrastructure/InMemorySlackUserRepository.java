package com.woowacourse.thankoo.alarm.infrastructure;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemorySlackUserRepository {

    private final Map<String, String> store;
    private final SlackClient slackClient;

    public String findUserToken(final String email) {
        String userToken = store.computeIfAbsent(email, slackClient::getUserToken);
        if (userToken == null) {
            throw new InvalidAlarmException(ErrorType.NOT_FOUND_SLACK_USER);
        }
        return userToken;
    }
}
