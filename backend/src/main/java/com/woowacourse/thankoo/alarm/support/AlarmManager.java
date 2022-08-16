package com.woowacourse.thankoo.alarm.support;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.util.List;

public class AlarmManager {

    private static final ThreadLocal<List<AlarmMessageRequest>> resources = new ThreadLocal<>();

    public static List<AlarmMessageRequest> getResources() {
        List<AlarmMessageRequest> alarmMessageEvent = resources.get();
        if (alarmMessageEvent == null) {
            throw new InvalidAlarmException(ErrorType.NOT_FOUND_ALARM_REQUEST);
        }
        return alarmMessageEvent;
    }

    public static void setResources(final AlarmMessageRequest request) {
        resources.set(List.of(request));
    }

    public static void setResources(final List<AlarmMessageRequest> request) {
        resources.set(request);
    }

    public static void clear() {
        resources.remove();
    }
}
