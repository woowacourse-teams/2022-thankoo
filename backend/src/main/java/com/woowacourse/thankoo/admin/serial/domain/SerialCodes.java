package com.woowacourse.thankoo.admin.serial.domain;

import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import com.woowacourse.thankoo.serial.domain.SerialCode;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;

@Getter
public class SerialCodes {

    private final static int MAX_SIZE = 100;

    private final List<SerialCode> values;

    public SerialCodes(final List<SerialCode> values) {
        List<SerialCode> couponSerials = List.copyOf(values);
        validate(values, couponSerials);
        this.values = values;
    }

    public static SerialCodes of(final int size, final CodeCreator codeCreator) {
        return new SerialCodes(create(size, codeCreator));
    }

    private static List<SerialCode> create(final int size, final CodeCreator codeCreator) {
        return IntStream.range(0, size)
                .mapToObj(it -> new SerialCode(codeCreator.create()))
                .collect(Collectors.toList());
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
        if (values.size() > MAX_SIZE) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL_SIZE);
        }
    }

    public List<CouponSerial> createCouponSerials(final Long memberId, final CouponSerialType couponSerialType) {
        return values.stream()
                .map(code -> new CouponSerial(code, memberId, couponSerialType, NOT_USED))
                .collect(Collectors.toList());
    }

    public List<String> getSerialCodeValues() {
        return values.stream()
                .map(SerialCode::getValue)
                .collect(Collectors.toList());
    }
}
