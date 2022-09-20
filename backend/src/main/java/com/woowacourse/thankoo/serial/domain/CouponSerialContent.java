package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponSerialContent {

    private static final int MAX_TITLE_LENGTH = 20;
    private static final int MAX_MESSAGE_LENGTH = 100;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    public CouponSerialContent(String title, String message) {
        title = title.strip();
        message = message.strip();
        validateTitleLength(title);
        validateMessageLength(message);
        this.title = title;
        this.message = message;
    }

    private void validateTitleLength(final String title) {
        if (title.isBlank() || title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL_TITLE);
        }
    }

    private void validateMessageLength(final String message) {
        if (message.isBlank() || message.length() > MAX_MESSAGE_LENGTH) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL_MESSAGE);
        }
    }
}
