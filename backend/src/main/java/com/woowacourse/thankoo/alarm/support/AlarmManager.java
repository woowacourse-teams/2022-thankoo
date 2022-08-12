package com.woowacourse.thankoo.alarm.support;

import com.woowacourse.thankoo.alarm.dto.AlarmMessageRequest;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class AlarmManager {

    private static final ThreadLocal<AlarmMessageRequest> resources = new ThreadLocal<>();

    public static AlarmMessageRequest getResources() {
        AlarmMessageRequest alarmMessageEvent = resources.get();
        if (alarmMessageEvent == null) {
            throw new RuntimeException(ErrorType.NOT_FOUND_ALARM_REQUEST.getMessage());
        }
        return alarmMessageEvent;
    }

    public static void setResources(final AlarmMessageRequest alarmMessageEvent) {
        resources.set(alarmMessageEvent);
    }

    public static void clear() {
        resources.remove();
    }
}
