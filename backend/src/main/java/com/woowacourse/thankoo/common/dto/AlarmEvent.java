package com.woowacourse.thankoo.common.dto;

import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import lombok.Getter;

@Getter
public abstract class AlarmEvent {

    private AlarmType alarmType;

    protected AlarmEvent(final AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public abstract Alarm toAlarm();
}
