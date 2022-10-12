package com.woowacourse.thankoo.common.dto;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import lombok.Getter;

@Getter
public abstract class AlarmEvent {

    private final String alarmType;

    protected AlarmEvent(final String alarmType) {
        this.alarmType = alarmType;
    }

    public abstract AlarmSpecification toAlarmSpecification();
}
