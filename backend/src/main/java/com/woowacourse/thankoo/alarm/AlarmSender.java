package com.woowacourse.thankoo.alarm;

public interface AlarmSender {

    void send(final String email, final AlarmMessage alarmMessage);
}
