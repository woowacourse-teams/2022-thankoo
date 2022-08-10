package com.woowacourse.thankoo.common.alarm.dto;

import com.woowacourse.thankoo.common.alarm.AlarmMessage;
import lombok.Getter;

@Getter
public class SlackMessageEvent {

    private final Long memberId;
    private final AlarmMessage alarmMessage;

    public SlackMessageEvent(final Long memberId, final AlarmMessage alarmMessage) {
        this.memberId = memberId;
        this.alarmMessage = alarmMessage;
    }
}
