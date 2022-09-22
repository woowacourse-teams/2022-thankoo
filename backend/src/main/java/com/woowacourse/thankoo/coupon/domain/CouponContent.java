package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponContent {

    private static final int MAX_TITLE_LENGTH = 20;
    private static final int MAX_MESSAGE_LENGTH = 100;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "coupon_type", nullable = false, length = 20)
    private CouponType couponType;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    public CouponContent(final CouponType couponType, String title, String message) {
        title = title.strip();
        message = message.strip();
        validateTitleLength(title);
        validateMessageLength(message);
        this.couponType = couponType;
        this.title = title;
        this.message = message;
    }

    public CouponContent(final String type, final String title, final String message) {
        this(CouponType.of(type), title, message);
    }

    private void validateTitleLength(final String title) {
        if (title.isBlank() || title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidCouponContentException(ErrorType.INVALID_COUPON_TITLE);
        }
    }

    private void validateMessageLength(final String message) {
        if (message.isBlank() || message.length() > MAX_MESSAGE_LENGTH) {
            throw new InvalidCouponContentException(ErrorType.INVALID_COUPON_MESSAGE);
        }
    }

    public boolean isCoffeeType() {
        return couponType.isCoffee();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponContent)) {
            return false;
        }
        CouponContent that = (CouponContent) o;
        return couponType == that.couponType && Objects.equals(title, that.title) && Objects.equals(
                message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponType, title, message);
    }

    @Override
    public String toString() {
        return "CouponContent{" +
                "couponType=" + couponType +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
