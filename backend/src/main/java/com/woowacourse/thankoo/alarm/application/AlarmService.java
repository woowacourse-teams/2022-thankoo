package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final MessageFormStrategyFactory messageFormStrategyFactory;
    private final AlarmSender alarmSender;

    public void send(final AlarmSpecification alarmSpecification) {
        MessageFormStrategy strategy = messageFormStrategyFactory.getStrategy(alarmSpecification.getAlarmType());
        Alarm alarm = Alarm.create(alarmSpecification);
        Message message = Message.of(alarm, strategy);
        alarmSender.send(message);
    }
}
