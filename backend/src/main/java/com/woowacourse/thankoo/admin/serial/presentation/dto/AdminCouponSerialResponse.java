package com.woowacourse.thankoo.admin.serial.presentation.dto;

import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminCouponSerialResponse {

    private Long serialId;
    private String code;
    private Long senderId;
    private String senderName;
    private String couponType;

    public AdminCouponSerialResponse(final Long serialId,
                                     final String code,
                                     final Long senderId,
                                     final String senderName,
                                     final String couponType) {
        this.serialId = serialId;
        this.code = code;
        this.senderId = senderId;
        this.senderName = senderName;
        this.couponType = couponType;
    }

    public static AdminCouponSerialResponse from(final CouponSerialMember couponSerialMember) {
        return new AdminCouponSerialResponse(
                couponSerialMember.getId(),
                couponSerialMember.getCode().getValue(),
                couponSerialMember.getSenderId(),
                couponSerialMember.getSenderName(),
                couponSerialMember.getCouponType().getValue());
    }
}
