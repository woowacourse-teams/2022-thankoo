package com.woowacourse.thankoo.admin.coupon.domain;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.coupon.exception.AdminInvalidCouponStatusException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AdminCouponStatus {

    ALL,
    NOT_USED,
    RESERVING,
    RESERVED,
    USED,
    EXPIRED;

    public static AdminCouponStatus of(final String name) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new AdminInvalidCouponStatusException(AdminErrorType.INVALID_COUPON_STATUS));
    }

    public List<String> getName() {
        if (isAll()) {
            return Arrays.stream(values())
                    .filter(couponStatus -> !couponStatus.isAll())
                    .map(AdminCouponStatus::name)
                    .collect(Collectors.toList());
        }
        return List.of(this.name());
    }

    private boolean isAll() {
        return this == ALL;
    }
}
