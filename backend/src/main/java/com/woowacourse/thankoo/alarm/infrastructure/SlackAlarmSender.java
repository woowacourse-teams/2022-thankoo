package com.woowacourse.thankoo.alarm.infrastructure;

import com.woowacourse.thankoo.alarm.AlarmSender;
import com.woowacourse.thankoo.alarm.infrastructure.dto.Attachments;
import com.woowacourse.thankoo.alarm.support.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackAlarmSender implements AlarmSender {

    private final SlackClient slackClient;
    private final InMemorySlackUserRepository slackUserRepository;

    @Async
    @Override
    public void send(final Message message) {
        for (String email : message.getEmails()) {
            try {
                String slackUserToken = slackUserRepository.findUserToken(email);
                slackClient.sendMessage(slackUserToken, Attachments.from(message.getTitle(), message.getContents()));
            } catch (Exception e) {
                log.warn("알람 전송 실패 {}", email, e);
            }
        }
    }
}
