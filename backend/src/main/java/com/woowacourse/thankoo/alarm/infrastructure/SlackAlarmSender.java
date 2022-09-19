package com.woowacourse.thankoo.alarm.infrastructure;

import com.woowacourse.thankoo.alarm.application.AlarmSender;
import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.infrastructure.dto.Attachments;
import com.woowacourse.thankoo.alarm.infrastructure.slack.CacheSlackUserRepository;
import com.woowacourse.thankoo.alarm.infrastructure.slack.SlackClient;
import com.woowacourse.thankoo.common.alert.SlackAlarmFailedEvent;
import com.woowacourse.thankoo.common.event.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackAlarmSender implements AlarmSender {

    private final SlackClient slackClient;
    private final CacheSlackUserRepository slackUserRepository;

    @Override
    public void send(final Message message) {
        for (String email : message.getEmails()) {
            send(message, email);
        }
    }

    private void send(final Message message, final String email) {
        try {
            sendSlackMessage(message, email);
        } catch (Exception e) {
            Events.publish(new SlackAlarmFailedEvent(message.getTitle(),
                    message.getTitleLink(),
                    email,
                    message.getContents()));
        }
    }

    private void sendSlackMessage(final Message message, final String email) {
        String slackUserToken = slackUserRepository.getTokenByEmail(email);
        slackClient.sendMessage(slackUserToken,
                Attachments.from(message.getTitle(), message.getTitleLink(), message.getContents()));
    }
}
