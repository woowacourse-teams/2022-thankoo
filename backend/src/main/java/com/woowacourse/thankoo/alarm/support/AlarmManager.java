package com.woowacourse.thankoo.alarm.support;

import com.woowacourse.thankoo.alarm.dto.AlarmMessageRequest;

public class AlarmManager {

    private static final ThreadLocal<AlarmMessageRequest> resources = new ThreadLocal<>();

    public static AlarmMessageRequest getResources() {
        AlarmMessageRequest alarmMessageEvent = resources.get();
        if (alarmMessageEvent == null) {
            throw new RuntimeException();
        }
        return alarmMessageEvent;
    }

    public static void setResources(AlarmMessageRequest alarmMessageEvent) {
        resources.set(alarmMessageEvent);
    }
}
