package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AlarmEventListener {

    private final AlarmService alarmService;

    private final MessageFormStrategyFactory messageFormStrategyFactory;
    private final AlarmSender alarmSender;

    //    @Async(value = "asyncTaskExecutor")
    @TransactionalEventListener
    public void handle(final AlarmEvent alarmEvent) {
        log.debug("alarm event = {}", alarmEvent);
//        alarmService.send(alarmEvent.toAlarmSpecification());
        Alarm alarm = Alarm.create(alarmEvent.toAlarmSpecification());
        MessageFormStrategy strategy = messageFormStrategyFactory.getStrategy(alarm.getAlarmType());
        Message message = Message.of(alarm, strategy);
        alarmSender.send(message);
        throw new RuntimeException();
    }
}
