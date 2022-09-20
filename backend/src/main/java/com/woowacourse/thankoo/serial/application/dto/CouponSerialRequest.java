package com.woowacourse.thankoo.serial.application.dto;

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
public class CouponSerialRequest {

    private Long memberId;
    private String couponType;
    private int quantity;
    private String title;
    private String message;

    public CouponSerialRequest(final Long memberId,
                               final String couponType,
                               final int quantity,
                               final String title,
                               final String message) {
        this.memberId = memberId;
        this.couponType = couponType;
        this.quantity = quantity;
        this.title = title;
        this.message = message;
    }

    public CouponSerial from(final SerialCode serialCode, final Long senderId) {
        return new CouponSerial(serialCode,
                senderId,
                CouponSerialType.of(couponType),
                CouponSerialStatus.NOT_USED,
                new CouponSerialContent(title, message));
    }
}
