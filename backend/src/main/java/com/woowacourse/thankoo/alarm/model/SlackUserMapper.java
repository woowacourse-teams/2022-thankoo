package com.woowacourse.thankoo.alarm.model;

import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUserResponse;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUsersResponse;
import com.woowacourse.thankoo.alarm.infrastructure.SlackClient;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SlackUserMapper {

    private final SlackClient slackClient;

    public Map<String, String> getSlackUsers() {
        SlackUsersResponse slackUsers = slackClient.getUsers();
        return slackUsers.getResponses()
                .stream()
                .filter(slackUserResponse -> Objects.nonNull(slackUserResponse.getProfile().getEmail()))
                .collect(Collectors.toConcurrentMap(
                        slackUserResponse -> slackUserResponse.getProfile().getEmail(),
                        SlackUserResponse::getId));
    }
}
