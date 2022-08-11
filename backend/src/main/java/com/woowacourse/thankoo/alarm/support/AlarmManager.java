package com.woowacourse.thankoo.alarm.support;

import com.woowacourse.thankoo.alarm.dto.AlarmMessageRequest;

public class AlarmManager {

    private static final ThreadLocal<AlarmMessageRequest> resources = new ThreadLocal<>();

    public static AlarmMessageRequest getResources() {
        AlarmMessageRequest alarmMessageEvent = resources.get();
        if (alarmMessageEvent == null) {
            throw new RuntimeException("전송하려는 알람이 존재하지 않습니다.");
        }
        return alarmMessageEvent;
    }

    public static void setResources(AlarmMessageRequest alarmMessageEvent) {
        resources.remove();
        resources.set(alarmMessageEvent);
    }

    public static void remove() {
        resources.remove();
    }
}
