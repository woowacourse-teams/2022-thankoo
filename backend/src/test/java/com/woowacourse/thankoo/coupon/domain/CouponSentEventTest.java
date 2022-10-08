package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CouponSentEvent 는 ")
class CouponSentEventTest {

    @DisplayName("알람 스펙으로 변경한다.")
    @ParameterizedTest
    @CsvSource(value = {"COFFEE:coupon_sent_coffee", "MEAL:coupon_sent_meal"}, delimiter = ':')
    void toAlarmSpecification(final CouponType couponType, final String alarmType) {
        CouponSentEvent couponSentEvent = CouponSentEvent.from(givenCoupons(couponType));
        AlarmSpecification alarmSpecification = couponSentEvent.toAlarmSpecification();

        assertAll(
                () -> assertThat(alarmSpecification.getAlarmType()).isEqualTo(alarmType),
                () -> assertThat(alarmSpecification.getTargetIds()).containsExactly(2L, 3L, 4L),
                () -> assertThat(alarmSpecification.getContents()).containsExactly("1", TITLE, couponType.getValue())
        );
    }

    private Coupons givenCoupons(final CouponType couponType) {
        List<Coupon> coupons = createRawCoupons(couponType);
        return new Coupons(coupons);
    }

    private List<Coupon> createRawCoupons(final CouponType couponType) {
        List<Coupon> coupons = new ArrayList<>();
        for (long id = 1; id < 4; id++) {
            coupons.add(new Coupon(id, 1L, 1L, id + 1, new CouponContent(couponType, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));
        }
        return coupons;
    }
}
