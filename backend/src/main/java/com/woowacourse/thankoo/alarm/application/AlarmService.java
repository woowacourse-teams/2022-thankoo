package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final MessageFormStrategyFactory messageFormStrategyFactory;
    private final AlarmSender alarmSender;

    public void send(final AlarmSpecification alarmSpecification) {
        Alarm alarm = Alarm.create(alarmSpecification);
        MessageFormStrategy strategy = messageFormStrategyFactory.getStrategy(alarm.getAlarmType());
        Message message = Message.of(alarm, strategy);
        alarmSender.send(message);
    }
}
