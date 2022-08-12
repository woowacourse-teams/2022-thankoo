package com.woowacourse.thankoo.alarm.infrastructure.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlackMessageRequest {

    private String channel;
    private String text;

    public SlackMessageRequest(final String channel, final String text) {
        this.channel = channel;
        this.text = text;
    }
}
