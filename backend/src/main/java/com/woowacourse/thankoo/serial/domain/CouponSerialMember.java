package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.coupon.domain.CouponType;
import lombok.Getter;

@Getter
public class CouponSerialMember {

    private final Long id;
    private final String code;
    private final Long memberId;
    private final String memberName;
    private final CouponType couponType;

    public CouponSerialMember(final Long id,
                              final String code,
                              final Long memberId,
                              final String memberName,
                              final String couponType) {
        this.id = id;
        this.code = code;
        this.memberId = memberId;
        this.memberName = memberName;
        this.couponType = CouponType.of(couponType);
    }
}
