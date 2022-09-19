package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SerialCode {

    private static final int CODE_LENGTH = 8;

    @Column(name = "code", nullable = false)
    private String value;

    public SerialCode(final String value) {
        validateCode(value);
        this.value = value;
    }

    private void validateCode(final String code) {
        if (!isValidCode(code)) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL);
        }
    }

    private static boolean isValidCode(final String code) {
        return !code.isBlank() && code.length() == CODE_LENGTH;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SerialCode)) {
            return false;
        }
        SerialCode that = (SerialCode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "SerialCode{" +
                "code='" + value + '\'' +
                '}';
    }
}
