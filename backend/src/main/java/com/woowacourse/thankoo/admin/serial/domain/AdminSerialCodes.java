package com.woowacourse.thankoo.admin.serial.domain;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.serial.excepion.AdminInvalidCouponSerialException;
import com.woowacourse.thankoo.serial.domain.SerialCode;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;

@Getter
public class AdminSerialCodes {

    private final static int MAX_SIZE = 100;

    private final List<SerialCode> values;

    public AdminSerialCodes(final List<SerialCode> values) {
        List<SerialCode> couponSerials = List.copyOf(values);
        validate(values, couponSerials);
        this.values = values;
    }

    public static AdminSerialCodes of(final int size, final AdminCodeCreator adminCodeCreator) {
        return new AdminSerialCodes(create(size, adminCodeCreator));
    }

    private static List<SerialCode> create(final int size, final AdminCodeCreator adminCodeCreator) {
        return IntStream.range(0, size)
                .mapToObj(it -> new SerialCode(adminCodeCreator.create()))
                .collect(Collectors.toList());
    }

    private void validate(final List<SerialCode> values, final List<SerialCode> couponSerials) {
        validateDuplicateCode(couponSerials);
        validateSize(values);
    }

    private void validateDuplicateCode(final List<SerialCode> values) {
        if (new HashSet<>(values).size() != values.size()) {
            throw new AdminInvalidCouponSerialException(AdminErrorType.DUPLICATE_COUPON_SERIAL);
        }
    }

    private void validateSize(final List<SerialCode> values) {
        if (values.size() > MAX_SIZE) {
            throw new AdminInvalidCouponSerialException(AdminErrorType.INVALID_COUPON_SERIAL_SIZE);
        }
    }

    public List<String> getSerialCodeValues() {
        return values.stream()
                .map(SerialCode::getValue)
                .collect(Collectors.toList());
    }
}
