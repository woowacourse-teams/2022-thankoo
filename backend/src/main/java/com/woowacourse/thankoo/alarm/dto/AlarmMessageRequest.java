package com.woowacourse.thankoo.alarm.dto;

import com.woowacourse.thankoo.alarm.AlarmMessage;
import java.util.List;
import lombok.Getter;

@Getter
public class AlarmMessageRequest {

    private final List<String> emails;
    private final AlarmMessage alarmMessage;

    public AlarmMessageRequest(final List<String> emails, final AlarmMessage alarmMessage) {
        this.emails = emails;
        this.alarmMessage = alarmMessage;
    }

    public AlarmMessageRequest(final String email, final AlarmMessage alarmMessage) {
        this.emails = List.of(email);
        this.alarmMessage = alarmMessage;
    }
}
