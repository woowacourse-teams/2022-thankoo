package com.woowacourse.thankoo.serial.presentation.dto;

import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponSerialResponse {

    private Long id;
    private Long organizationId;
    private String organizationName;
    private Long senderId;
    private String senderName;
    private String couponType;

    public CouponSerialResponse(final Long id,
                                final Long organizationId,
                                final String organizationName,
                                final Long senderId,
                                final String senderName,
                                final String couponType) {
        this.id = id;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.senderId = senderId;
        this.senderName = senderName;
        this.couponType = couponType;
    }

    public static CouponSerialResponse of(final CouponSerialMember couponSerialMember, final String organizationName) {
        return new CouponSerialResponse(couponSerialMember.getId(),
                couponSerialMember.getOrganizationId(),
                organizationName,
                couponSerialMember.getSenderId(),
                couponSerialMember.getSenderName(),
                couponSerialMember.getCouponType().getValue().toLowerCase());
    }

    @Override
    public String toString() {
        return "CouponSerialResponse{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", couponType='" + couponType + '\'' +
                '}';
    }
}
