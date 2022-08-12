package com.woowacourse.thankoo.alarm;

public interface AlarmSender {

    void send(String email, AlarmMessage alarmMessage);
}
