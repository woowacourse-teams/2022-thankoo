package com.woowacourse.thankoo.common.alarm;

import com.woowacourse.thankoo.common.alarm.cache.SlackUser;
import com.woowacourse.thankoo.common.alarm.cache.SlackUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackAlarmService {

    private final SlackUserRepository cachedSlackUserRepository;
    private final SlackMessageSender slackMessageSender;

    public void sendMessage(final String email, final SlackMessage message) {
        SlackUser cachedSlackUser = cachedSlackUserRepository.getCachedSlackUser(email);
        String slackUserId = cachedSlackUser.getSlackUserId();
        slackMessageSender.sendMessage(slackUserId, message);
    }
}
