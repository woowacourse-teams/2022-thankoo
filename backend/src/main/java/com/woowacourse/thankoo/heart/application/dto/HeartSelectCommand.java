package com.woowacourse.thankoo.heart.application.dto;

import lombok.Getter;

@Getter
public class HeartSelectCommand {

    private final Long senderId;
    private final Long receiverId;
    private final Long organizationId;

    public HeartSelectCommand(final Long senderId, final Long receiverId, final Long organizationId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "HeartSelectCommand{" +
                "senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", organizationId=" + organizationId +
                '}';
    }
}
