package com.woowacourse.thankoo.serial.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class CouponSerialMember {

    private final Long id;
    private final Long organizationId;
    private final SerialCode code;
    private final Long senderId;
    private final String senderName;
    private final CouponSerialType couponType;
    private final CouponSerialStatus status;

    public CouponSerialMember(final Long id,
                              final Long organizationId,
                              final String code,
                              final Long senderId,
                              final String senderName,
                              final String couponType,
                              final String status) {

        this.id = id;
        this.organizationId = organizationId;
        this.code = new SerialCode(code);
        this.senderId = senderId;
        this.senderName = senderName;
        this.couponType = CouponSerialType.of(couponType);
        this.status = CouponSerialStatus.valueOf(status);
    }

    public boolean isUsed() {
        return status == CouponSerialStatus.USED;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponSerialMember)) {
            return false;
        }
        CouponSerialMember that = (CouponSerialMember) o;
        return Objects.equals(id, that.id) && Objects.equals(organizationId, that.organizationId)
                && Objects.equals(code, that.code) && Objects.equals(senderId, that.senderId)
                && Objects.equals(senderName, that.senderName) && couponType == that.couponType
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organizationId, code, senderId, senderName, couponType, status);
    }

    @Override
    public String toString() {
        return "CouponSerialMember{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", code=" + code +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", couponType=" + couponType +
                ", status=" + status +
                '}';
    }
}
