package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.application.dto.Message;

public interface AlarmSender {

    void send(Message message);
}
