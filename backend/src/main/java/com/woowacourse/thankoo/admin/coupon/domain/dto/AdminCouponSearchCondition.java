package com.woowacourse.thankoo.admin.coupon.domain.dto;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponStatus;
import com.woowacourse.thankoo.admin.coupon.exception.AdminInvalidCouponSearchConditionException;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class AdminCouponSearchCondition {

    private final List<String> statuses;
    private final String startDate;
    private final String endDate;

    private AdminCouponSearchCondition(final List<String> statuses, final String startDate, final String endDate) {
        this.statuses = statuses;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static AdminCouponSearchCondition of(final AdminCouponStatus couponStatus,
                                                final LocalDate startDate,
                                                final LocalDate endDate) {
        validateDate(startDate, endDate);
        return new AdminCouponSearchCondition(couponStatus.getName(), startDate.toString(), endDate.toString());
    }

    private static void validateDate(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new AdminInvalidCouponSearchConditionException(AdminErrorType.INVALID_COUPON_SEARCH_CONDITION);
        }
    }
}
