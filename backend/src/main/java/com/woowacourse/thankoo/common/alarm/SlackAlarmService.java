package com.woowacourse.thankoo.common.alarm;

import com.woowacourse.thankoo.common.alarm.cache.CachedSlackUser;
import com.woowacourse.thankoo.common.alarm.cache.CachedSlackUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackAlarmService {

    private final CachedSlackUserRepository cachedSlackUserRepository;
    private final SlackMessageSender slackMessageSender;

    public void sendMessage(final String email, final SlackMessage message) {
        CachedSlackUser cachedSlackUser = cachedSlackUserRepository.getCachedSlackUser(email);
        String slackUserId = cachedSlackUser.getSlackUserId();
        slackMessageSender.sendMessage(slackUserId, message);
    }
}
