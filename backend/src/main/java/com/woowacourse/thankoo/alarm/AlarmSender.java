package com.woowacourse.thankoo.alarm;

import com.woowacourse.thankoo.alarm.infrastructure.dto.Attachments;

public interface AlarmSender {

    void send(String email, Attachments attachments);
}
