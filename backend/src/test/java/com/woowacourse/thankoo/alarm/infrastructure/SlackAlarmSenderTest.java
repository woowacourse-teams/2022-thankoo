package com.woowacourse.thankoo.alarm.infrastructure;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.infrastructure.slack.CacheSlackUserRepository;
import com.woowacourse.thankoo.alarm.infrastructure.slack.SlackClient;
import com.woowacourse.thankoo.common.alert.SlackAlarmFailedEvent;
import com.woowacourse.thankoo.common.alert.SlackAlarmFailedListener;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

@DisplayName("SlackAlarmSender 는 ")
@ApplicationTest
class SlackAlarmSenderTest {

    private SlackAlarmSender slackAlarmSender;

    @MockBean
    private SlackClient slackClient;

    @MockBean
    private CacheSlackUserRepository slackUserRepository;

    @MockBean
    private SlackAlarmFailedListener slackAlarmFailedListener;

    @BeforeEach
    void setUp() {
        slackAlarmSender = new SlackAlarmSender(slackClient, slackUserRepository);
    }

    @DisplayName("실패할 경우 슬랙 알람을 전송한다.")
    @Test
    void sendSlackAlertIfFailed() {
        given(slackUserRepository.getTokenByEmail(anyString())).willThrow(HttpClientErrorException.class);

        Message message = new Message("실패한 알람이올시다!",
                "http://localhost:8080",
                List.of("hoho@email.com"),
                List.of("sender email", "title: title", "type: coffee"));
        slackAlarmSender.send(message);

        verify(slackAlarmFailedListener, times(1)).handle(any(SlackAlarmFailedEvent.class));
    }
}
