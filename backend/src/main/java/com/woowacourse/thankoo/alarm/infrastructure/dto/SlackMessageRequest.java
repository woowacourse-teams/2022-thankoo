package com.woowacourse.thankoo.alarm.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlackMessageRequest {

    private String channel;

    @JsonProperty("attachments")
    private List<Attachments> attachments;

    private SlackMessageRequest(final String channel, final List<Attachments> attachments) {
        this.channel = channel;
        this.attachments = attachments;
    }

    public static SlackMessageRequest of(
            final String channel,
            final Attachments attachments) {
        return new SlackMessageRequest(channel, List.of(attachments));
    }
}
