package com.woowacourse.thankoo.alarm;

import com.woowacourse.thankoo.alarm.infrastructure.dto.AttachmentsRequest;

public interface AlarmSender {

    void send(String email, AttachmentsRequest request);
}
