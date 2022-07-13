package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.USED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.exception.InvalidCouponStatusException;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("CouponStatusGroup 은 ")
class CouponStatusGroupTest {

    @DisplayName("title 에 따른 CouponStatus 들을 반환한다")
    @ParameterizedTest
    @MethodSource("provideRightStatusNames")
    void findStatusNames(String title, List<String> statusNames) {
        assertThat(CouponStatusGroup.findStatusNames(title)).containsAll(statusNames);
    }

    @DisplayName("title 이 없을 때 에러가 발생한다.")
    @Test
    void findStatusNamesException() {
        assertThatThrownBy(() -> CouponStatusGroup.findStatusNames("a"))
                .isInstanceOf(InvalidCouponStatusException.class)
                .hasMessage("잘못된 쿠폰 상태입니다.");
    }

    private static Stream<Arguments> provideRightStatusNames() {
        return Stream.of(
                Arguments.of(NOT_USED, List.of(CouponStatus.NOT_USED.name(), CouponStatus.RESERVED.name())),
                Arguments.of(USED, List.of(CouponStatus.USED.name(), CouponStatus.EXPIRED.name()))
        );
    }
}
