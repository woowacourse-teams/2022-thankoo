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

@DisplayName("ReservationRepliedEvent 는 ")
class ReservationRepliedEventTest {

    @DisplayName("알람 스펙으로 변경한다.")
    @Test
    void toAlarmSpecification() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        ReservationRepliedEvent reservationRepliedEvent = ReservationRepliedEvent.of(2L, coupon,
                ReservationStatus.ACCEPT);
        AlarmSpecification alarmSpecification = reservationRepliedEvent.toAlarmSpecification();

        assertAll(
                () -> assertThat(alarmSpecification.getAlarmType()).isEqualTo(AlarmSpecification.RESERVATION_REPLIED),
                () -> assertThat(alarmSpecification.getTargetIds()).containsExactly(2L),
                () -> assertThat(alarmSpecification.getContents()).containsExactly("1", TITLE, "ACCEPT")
        );
    }
}
