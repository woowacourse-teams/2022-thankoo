package com.woowacourse.thankoo.heart.application.dto;

import com.woowacourse.thankoo.heart.presentation.dto.HeartRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HeartSendRequest {

    private Long organizationId;
    private Long senderId;
    private Long receiverId;

    public HeartSendRequest(final Long organizationId, final Long senderId, final Long receiverId) {
        this.organizationId = organizationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public static HeartSendRequest from(final Long senderId, final HeartRequest heartRequest) {
        return new HeartSendRequest(heartRequest.getOrganizationId(), senderId, heartRequest.getReceiverId());
    }

    @Override
    public String toString() {
        return "HeartSendRequest{" +
                "organizationId=" + organizationId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }
}
