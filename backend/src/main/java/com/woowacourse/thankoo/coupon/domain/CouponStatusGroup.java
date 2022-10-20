package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponStatusException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum CouponStatusGroup {

    NOT_USED("not-used", Arrays.asList(CouponStatus.NOT_USED, CouponStatus.RESERVING, CouponStatus.RESERVED)),
    USED("used", Arrays.asList(CouponStatus.USED, CouponStatus.EXPIRED, CouponStatus.IMMEDIATELY_USED)),
    ALL("all", Arrays.asList(CouponStatus.NOT_USED, CouponStatus.RESERVING, CouponStatus.RESERVED,
            CouponStatus.USED, CouponStatus.EXPIRED, CouponStatus.IMMEDIATELY_USED));

    private final String title;
    private final List<CouponStatus> statuses;

    CouponStatusGroup(final String title, final List<CouponStatus> statuses) {
        this.title = title;
        this.statuses = statuses;
    }

    public static List<String> findStatusNames(final String title) {
        return Arrays.stream(values())
                .filter(group -> group.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new InvalidCouponStatusException(ErrorType.INVALID_COUPON_STATUS))
                .toNames();
    }

    private List<String> toNames() {
        return statuses.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
