package com.woowacourse.thankoo.admin.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.admin.coupon.exception.AdminInvalidCouponStatusException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("AdminCouponStatus 는 ")
class AdminCouponStatusTest {

    @DisplayName("쿠폰 타입을 생성할 때 ")
    @Nested
    class CreateCouponStatus {

        @DisplayName("쿠폰 타입 이름으로 쿠폰 타입을 찾아 정상적으로 생성한다.")
        @ParameterizedTest
        @ValueSource(strings = {"NOT_USED", "RESERVING", "RESERVED", "USED", "EXPIRED", "ALL"})
        void of(final String name) {
            AdminCouponStatus couponStatus = AdminCouponStatus.of(name);

            assertThat(couponStatus).isNotNull();
        }

        @DisplayName("존재하지 않는 쿠폰 타입 이름일 경우 예외가 발생한다.")
        @Test
        void ofWithInvalidCouponStatus() {
            assertThatThrownBy(() -> AdminCouponStatus.of("lala"))
                    .isInstanceOf(AdminInvalidCouponStatusException.class)
                    .hasMessage("존재하지 않는 쿠폰 타입입니다.");
        }
    }

    @DisplayName("쿠폰 타입 이름을 조회할 때 ")
    @Nested
    class GetName {

        @DisplayName("정상적으로 조회힌다.")
        @ParameterizedTest
        @ValueSource(strings = {"NOT_USED", "RESERVING", "RESERVED", "USED", "EXPIRED"})
        void getName(final String name) {
            AdminCouponStatus couponStatus = AdminCouponStatus.of(name);

            assertThat(couponStatus.getName()).containsOnly(name);
        }

        @DisplayName("ALL 일 경우 ALL 을 제외한 모든 이름을 조회한다.")
        @Test
        void getNameOfAll() {
            AdminCouponStatus couponStatus = AdminCouponStatus.ALL;
            List<String> names = couponStatus.getName();

            assertAll(
                    () -> assertThat(names).doesNotContain("ALL"),
                    () -> assertThat(names).hasSize(AdminCouponStatus.values().length - 1)
            );
        }
    }
}
