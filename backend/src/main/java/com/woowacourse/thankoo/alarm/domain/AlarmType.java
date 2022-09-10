package com.woowacourse.thankoo.alarm.domain;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmTypeException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.util.Arrays;

public enum AlarmType {

    COUPON_SENT_COFFEE,
    COUPON_SENT_MEAL,
    RESERVATION_SENT;

    public static AlarmType from(final String type) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new InvalidAlarmTypeException(ErrorType.INVALID_ALARM_TYPE));
    }
}
