package com.woowacourse.thankoo.serial.application.dto;

import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponSerialRequest {

    private String coachName;
    private String couponType;
    private String code;

    public CouponSerialRequest(final String coachName, final String couponType, final String code) {
        this.coachName = coachName;
        this.couponType = couponType;
        this.code = code;
    }

    public CouponSerial toEntity(final Long memberId) {
        return new CouponSerial(code, memberId, CouponType.of(couponType));
    }
}
