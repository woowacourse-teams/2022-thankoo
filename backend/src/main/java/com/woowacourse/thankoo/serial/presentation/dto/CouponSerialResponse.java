package com.woowacourse.thankoo.serial.presentation.dto;

import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponSerialResponse {

    private Long id;
    private Long senderId;
    private String senderName;
    private String couponType;

    public CouponSerialResponse(final Long id, final Long senderId, final String senderName, final String couponType) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
        this.couponType = couponType;
    }

    public static CouponSerialResponse from(final CouponSerialMember couponSerialMember) {
        return new CouponSerialResponse(couponSerialMember.getId(),
                couponSerialMember.getSenderId(),
                couponSerialMember.getSenderName(),
                couponSerialMember.getCouponType().getValue().toLowerCase());
    }

    @Override
    public String toString() {
        return "CouponSerialResponse{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", couponType='" + couponType + '\'' +
                '}';
    }
}
