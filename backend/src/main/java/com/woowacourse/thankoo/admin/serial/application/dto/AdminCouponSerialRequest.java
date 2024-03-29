package com.woowacourse.thankoo.admin.serial.application.dto;

import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialContent;
import com.woowacourse.thankoo.serial.domain.CouponSerialStatus;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import com.woowacourse.thankoo.serial.domain.SerialCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminCouponSerialRequest {

    private Long memberId;
    private Long organizationId;
    private String couponType;
    private int quantity;
    private String title;
    private String message;

    public AdminCouponSerialRequest(final Long memberId,
                                    final Long organizationId,
                                    final String couponType,
                                    final int quantity,
                                    final String title,
                                    final String message) {
        this.memberId = memberId;
        this.organizationId = organizationId;
        this.couponType = couponType;
        this.quantity = quantity;
        this.title = title;
        this.message = message;
    }

    public CouponSerial from(final SerialCode serialCode, final Long senderId) {
        return new CouponSerial(organizationId,
                serialCode,
                senderId,
                CouponSerialType.of(couponType),
                CouponSerialStatus.NOT_USED,
                new CouponSerialContent(title, message));
    }

    @Override
    public String toString() {
        return "AdminCouponSerialRequest{" +
                "memberId=" + memberId +
                ", organizationId=" + organizationId +
                ", couponType='" + couponType + '\'' +
                ", quantity=" + quantity +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
