package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponContent {

    private static final int MAX_TITLE_LENGTH = 20;
    private static final int MAX_MESSAGE_LENGTH = 50;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "coupon_type", nullable = false, length = 20)
    private CouponType couponType;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    public CouponContent(final CouponType couponType, final String title, final String message) {
        validateTitleLength(title);
        validateMessageLength(message);
        this.couponType = couponType;
        this.title = title;
        this.message = message;
    }

    private void validateTitleLength(final String title) {
        if (title.isEmpty() || title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidCouponContentException(ErrorType.INVALID_COUPON_TITLE);
        }
    }

    private void validateMessageLength(final String message) {
        if (message.isEmpty() || message.length() > MAX_MESSAGE_LENGTH) {
            throw new InvalidCouponContentException(ErrorType.INVALID_COUPON_MESSAGE);
        }
    }
}
