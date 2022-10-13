package com.woowacourse.thankoo.admin.serial.presentation.dto;

import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminCouponSerialResponse {

    private Long id;
    private Long organizationId;
    private String code;
    private Long senderId;
    private String senderName;
    private String couponType;

    public AdminCouponSerialResponse(final Long id,
                                     final Long organizationId,
                                     final String code,
                                     final Long senderId,
                                     final String senderName,
                                     final String couponType) {
        this.id = id;
        this.organizationId = organizationId;
        this.code = code;
        this.senderId = senderId;
        this.senderName = senderName;
        this.couponType = couponType;
    }

    public static AdminCouponSerialResponse from(final CouponSerialMember couponSerialMember) {
        return new AdminCouponSerialResponse(
                couponSerialMember.getOrganizationId(),
                couponSerialMember.getId(),
                couponSerialMember.getCode().getValue(),
                couponSerialMember.getSenderId(),
                couponSerialMember.getSenderName(),
                couponSerialMember.getCouponType().getValue());
    }

    @Override
    public String toString() {
        return "AdminCouponSerialResponse{" +
                "serialId=" + serialId +
                ", code='" + code + '\'' +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", couponType='" + couponType + '\'' +
                '}';
    }
}
