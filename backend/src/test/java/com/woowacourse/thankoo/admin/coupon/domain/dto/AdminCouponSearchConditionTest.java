package com.woowacourse.thankoo.admin.coupon.domain.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponStatus;
import com.woowacourse.thankoo.admin.coupon.exception.AdminInvalidCouponSearchConditionException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AdminCouponSearchCondition 는 ")
class AdminCouponSearchConditionTest {

    @DisplayName("쿠폰 검색 조건의 종료 날짜보다 시작 날짜가 더 빠른 경우 예외가 발생한다.")
    @Test
    void ofWithInvalidDate() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1L);
        assertThatThrownBy(() -> AdminCouponSearchCondition.of(AdminCouponStatus.RESERVED, startDate, endDate))
                .isInstanceOf(AdminInvalidCouponSearchConditionException.class)
                .hasMessage("쿠폰 검색 조건이 올바르지 않습니다.");
    }
}
