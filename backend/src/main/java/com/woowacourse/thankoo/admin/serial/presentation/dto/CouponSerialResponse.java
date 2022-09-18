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
    private Long senderId;
    private String senderName;
    private String couponType;

    public CouponSerialResponse(final Long id,
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

    public static CouponSerialResponse from(final CouponSerialMember couponSerialMember) {
        return new CouponSerialResponse(
                couponSerialMember.getId(),
                couponSerialMember.getCode(),
                couponSerialMember.getSenderId(),
                couponSerialMember.getSenderName(),
                couponSerialMember.getCouponType());
    }
}
