package com.woowacourse.thankoo.alarm.infrastructure;

import com.woowacourse.thankoo.alarm.AlarmMessage;
import com.woowacourse.thankoo.alarm.AlarmSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
@Slf4j
public class SlackAlarmSender implements AlarmSender {

    private final SlackClient slackClient;
    private final InMemorySlackUserRepository slackUserRepository;

    @Async
    @Override
    public void send(final String email, final AlarmMessage alarmMessage) {
        try {
            String slackUserToken = slackUserRepository.findUserToken(email);
            slackClient.sendMessage(slackUserToken, alarmMessage);
        } catch (Exception e) {
            log.warn("알람 전송 실패 {}", email, e);
        }
    }
}
