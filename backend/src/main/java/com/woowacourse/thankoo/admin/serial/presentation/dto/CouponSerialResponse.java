package com.woowacourse.thankoo.admin.serial.presentation.dto;

import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponSerialResponse {

    private Long id;
    private String code;
    private Long memberId;
    private String memberName;
    private String couponType;

    public CouponSerialResponse(final Long id,
                                final String code,
                                final Long memberId,
                                final String memberName,
                                final String couponType) {
        this.id = id;
        this.code = code;
        this.memberId = memberId;
        this.memberName = memberName;
        this.couponType = couponType;
    }

    public static CouponSerialResponse from(final CouponSerialMember couponSerialMember) {
        return new CouponSerialResponse(
                couponSerialMember.getId(),
                couponSerialMember.getCode(),
                couponSerialMember.getSenderId(),
                couponSerialMember.getSenderName(),
                couponSerialMember.getCouponType());
    }
}
