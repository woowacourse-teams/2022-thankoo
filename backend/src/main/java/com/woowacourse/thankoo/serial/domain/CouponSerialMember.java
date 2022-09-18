package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import lombok.Getter;

@Getter
public class CouponSerialMember {

    private final Long id;
    private final String code;
    private final Long senderId;
    private final String senderName;
    private final String couponType;

    public CouponSerialMember(final Long id,
                              final String code,
                              final Long senderId,
                              final String senderName,
                              final String couponType) {
        this.id = id;
        this.code = code;
        this.senderId = senderId;
        this.senderName = senderName;
        this.couponType = couponType;
    }

    public Coupon createCoupon(final Long receiverId, CouponContent couponContent) {
        return new Coupon(senderId, receiverId, couponContent, NOT_USED);
    }
}
