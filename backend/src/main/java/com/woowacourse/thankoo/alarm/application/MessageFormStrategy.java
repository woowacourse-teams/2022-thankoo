package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;

public interface MessageFormStrategy {

    Message createFormat(Alarm alarm);

    AlarmType getAlarmType();
}
