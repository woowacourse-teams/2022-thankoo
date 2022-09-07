package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.AlarmSender;
import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventListener {

    private final MessageFormStrategyFactory messageFormStrategyFactory;
    private final AlarmSender alarmSender;

    @Async
    @TransactionalEventListener
    public void handle(AlarmEvent alarmEvent) {
        log.debug("alarm event = {}", alarmEvent);
        MessageFormStrategy strategy = messageFormStrategyFactory.getStrategy(alarmEvent.getAlarmType());
        Message message = strategy.createFormat(alarmEvent.toAlarm());
        alarmSender.send(message);
    }
}
