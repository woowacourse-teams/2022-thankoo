package com.woowacourse.thankoo.common.alert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SlackAlarmFailedListener {

    @SlackLogger
    @EventListener
    public void handle(final SlackAlarmFailedEvent slackAlarmFailedEvent) {
        log.warn("알람 전송 실패 - 내용 : {}", slackAlarmFailedEvent);
    }
}
