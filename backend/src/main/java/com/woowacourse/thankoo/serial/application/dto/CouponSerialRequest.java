package com.woowacourse.thankoo.serial.application.dto;

import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponSerialRequest {

    private Long memberId;
    private String couponType;
    private String code;

    public CouponSerialRequest(final Long memberId, final String couponType, final String code) {
        this.memberId = memberId;
        this.couponType = couponType;
        this.code = code;
    }

    public CouponSerial toEntity(final Long memberId) {
        return new CouponSerial(code, memberId, CouponSerialType.of(couponType));
    }
}
