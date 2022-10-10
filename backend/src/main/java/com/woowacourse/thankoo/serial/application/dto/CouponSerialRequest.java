package com.woowacourse.thankoo.serial.application.dto;

import lombok.Getter;

@Getter
public class CouponSerialRequest {

    private final Long memberId;
    private final Long organizationId;
    private final String code;

    public CouponSerialRequest(final Long memberId, final Long organizationId, final String code) {
        this.memberId = memberId;
        this.organizationId = organizationId;
        this.code = code;
    }
}
