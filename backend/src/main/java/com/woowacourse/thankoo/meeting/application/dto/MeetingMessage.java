package com.woowacourse.thankoo.meeting.application.dto;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingMessage {

    private static final String PRETEXT = "\uD83E\uDD70 오늘은 미팅이 있는 날이에요!!";
    private static final String TITLE_LINK = "https://thankoo.co.kr/meetings";

    public static Message of(final List<String> emails) {
        return Message.builder()
                .titleLink(TITLE_LINK)
                .email(emails)
                .title(PRETEXT)
                .contents(Collections.emptyList())
                .build();
    }

    @Override
    public String toString() {
        return "MeetingMessage{}";
    }
}
