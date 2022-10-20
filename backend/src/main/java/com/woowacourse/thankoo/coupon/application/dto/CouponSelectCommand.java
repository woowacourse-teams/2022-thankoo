package com.woowacourse.thankoo.coupon.application.dto;

import lombok.Getter;

@Getter
public class CouponSelectCommand {

    private final Long organizationId;
    private final Long memberId;
    private final String status;

    public CouponSelectCommand(final Long organizationId, final Long memberId, final String status) {
        this.organizationId = organizationId;
        this.memberId = memberId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CouponSelectCommand{" +
                "organizationId=" + organizationId +
                ", memberId=" + memberId +
                ", status='" + status + '\'' +
                '}';
    }
}
