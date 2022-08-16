package com.woowacourse.thankoo.alarm.support;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class AlarmManager {

    private static final ThreadLocal<Message> resources = new ThreadLocal<>();

    public static Message getResources() {
        Message message = resources.get();
        if (message == null) {
            throw new InvalidAlarmException(ErrorType.NOT_FOUND_ALARM_REQUEST);
        }
        return message;
    }

    public static void setResources(final Message message) {
        resources.set(message);
    }

    public static void clear() {
        resources.remove();
    }
}
