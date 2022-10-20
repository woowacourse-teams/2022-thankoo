package com.woowacourse.thankoo.heart.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HeartRequest {

    private Long organizationId;
    private Long receiverId;

    public HeartRequest(final Long organizationId, final Long receiverId) {
        this.organizationId = organizationId;
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "HeartRequest{" +
                "organizationId=" + organizationId +
                ", receiverId=" + receiverId +
                '}';
    }
}
