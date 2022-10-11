package com.woowacourse.thankoo.serial.domain;

import lombok.Getter;

@Getter
public class CouponSerialMember {

    private final Long id;
    private final SerialCode code;
    private final Long senderId;
    private final String senderName;
    private final CouponSerialType couponType;
    private final CouponSerialStatus status;

    public CouponSerialMember(final Long id,
                              final String code,
                              final Long senderId,
                              final String senderName,
                              final String couponType,
                              final String status) {

        this.id = id;
        this.code = new SerialCode(code);
        this.senderId = senderId;
        this.senderName = senderName;
        this.couponType = CouponSerialType.of(couponType);
        this.status = CouponSerialStatus.valueOf(status);
    }

    public boolean isUsed() {
        return status == CouponSerialStatus.USED;
    }
}
