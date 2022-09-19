package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Getter
public class SerialCodes {

    private final List<SerialCode> values;

    public SerialCodes(List<SerialCode> values) {
        List<SerialCode> couponSerials = List.copyOf(values);
        validate(values, couponSerials);
        this.values = values;
    }

    private void validate(final List<SerialCode> values, final List<SerialCode> couponSerials) {
        validateDuplicateCode(couponSerials);
        validateSize(values);
    }

    private void validateDuplicateCode(final List<SerialCode> values) {
        if (new HashSet<>(values).size() != values.size()) {
            throw new InvalidCouponSerialException(ErrorType.DUPLICATE_COUPON_SERIAL);
        }
    }

    private void validateSize(final List<SerialCode> values) {
        if (Set.of(values).size() != values.size()) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL_SIZE);
        }
    }
}
