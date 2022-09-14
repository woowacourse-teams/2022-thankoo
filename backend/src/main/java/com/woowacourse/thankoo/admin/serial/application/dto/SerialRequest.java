package com.woowacourse.thankoo.admin.serial.application.dto;

import com.woowacourse.thankoo.admin.serial.domain.Serial;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SerialRequest {

    private String coachName;
    private String couponType;
    private String code;

    public SerialRequest(final String coachName, final String couponType, final String code) {
        this.coachName = coachName;
        this.couponType = couponType;
        this.code = code;
    }

    public Serial toEntity(final Long memberId) {
        return new Serial(code, memberId, CouponType.of(couponType));
    }
}
