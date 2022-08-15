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
    private List<AttachmentsRequest> attachmentsRequest;

    private SlackMessageRequest(final String channel, final List<AttachmentsRequest> attachmentsRequest) {
        this.channel = channel;
        this.attachmentsRequest = attachmentsRequest;
    }

    public static SlackMessageRequest of(
            final String channel,
            final AttachmentsRequest attachmentsRequest) {
        return new SlackMessageRequest(channel, List.of(attachmentsRequest));
    }
}
