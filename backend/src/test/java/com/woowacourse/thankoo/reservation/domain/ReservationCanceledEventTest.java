package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReservationDeleteEvent 는 ")
class ReservationCanceledEventTest {

    @DisplayName("알람 스펙으로 변경한다.")
    @Test
    void toAlarmSpecification() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);

        ReservationCanceledEvent reservationCanceledEvent = ReservationCanceledEvent.of(coupon, 2L);
        AlarmSpecification alarmSpecification = reservationCanceledEvent.toAlarmSpecification();

        assertAll(
                () -> assertThat(alarmSpecification.getAlarmType()).isEqualTo(AlarmSpecification.RESERVATION_CANCELED),
                () -> assertThat(alarmSpecification.getTargetIds()).containsExactly(1L),
                () -> assertThat(alarmSpecification.getContents()).containsExactly("2", TITLE)
        );
    }
}
