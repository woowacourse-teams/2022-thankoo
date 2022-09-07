package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.support.Message;

public interface MessageFormStrategy {

    Message createFormat(Alarm alarm);

    AlarmType getAlarmType();
}
