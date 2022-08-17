package com.woowacourse.thankoo.meeting.application.dto;

import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.alarm.support.Message.Builder;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingMessage {

    public static final String PRETEXT = "\uD83E\uDD70 오늘은 미팅이 있는 날이에요!!";

    public static Message of(final List<String> emails) {
        return new Builder()
                .email(emails)
                .title(PRETEXT)
                .build();
    }
}
