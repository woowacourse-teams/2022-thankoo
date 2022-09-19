package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
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

    public Coupon createCoupon(final Long receiverId, CouponContent couponContent) {
        return new Coupon(senderId, receiverId, couponContent, NOT_USED);
    }

    public boolean isUsed() {
        return status == CouponSerialStatus.USED;
    }

    public CouponSerial createUsedCouponSerial() {
        return new CouponSerial(id, code, senderId, couponType, CouponSerialStatus.USED);
    }
}
