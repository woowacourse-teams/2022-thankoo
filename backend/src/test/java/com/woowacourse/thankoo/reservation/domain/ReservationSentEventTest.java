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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReservationSentEvent 는 ")
class ReservationSentEventTest {

    private static final Long ORGANIZATION_ID = 1L;

    @DisplayName("알람 스펙으로 변경한다.")
    @Test
    void toAlarmSpecification() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
        Long receiverId = 2L;

        Coupon coupon = new Coupon(ORGANIZATION_ID, 1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        Reservation reservation = Reservation.reserve(futureDate,
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.WAITING,
                receiverId,
                coupon);

        ReservationSentEvent reservationSentEvent = ReservationSentEvent.from(reservation);
        AlarmSpecification alarmSpecification = reservationSentEvent.toAlarmSpecification();

        String format = futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        assertAll(
                () -> assertThat(alarmSpecification.getAlarmType()).isEqualTo(AlarmSpecification.RESERVATION_SENT),
                () -> assertThat(alarmSpecification.getOrganizationId()).isEqualTo(ORGANIZATION_ID),
                () -> assertThat(alarmSpecification.getTargetIds()).containsExactly(1L),
                () -> assertThat(alarmSpecification.getContents()).containsExactly("2", TITLE, format)
        );
    }
}
