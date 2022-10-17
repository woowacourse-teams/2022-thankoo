package com.woowacourse.thankoo.coupon.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponRequest {

    private List<Long> receiverIds;
    private Long organizationId;
    private ContentRequest content;

    public CouponRequest(final List<Long> receiverIds, final Long organizationId, final ContentRequest content) {
        this.receiverIds = receiverIds;
        this.organizationId = organizationId;
        this.content = content;
    }

    public CouponCommand toCouponCommand(final Long senderId) {
        return new CouponCommand(organizationId, senderId, receiverIds, content.toCouponCommand());
    }

    @Override
    public String toString() {
        return "CouponRequest{" +
                "receiverIds=" + receiverIds +
                ", organizationId=" + organizationId +
                ", content=" + content +
                '}';
    }
}
