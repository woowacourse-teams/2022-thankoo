<<<<<<< HEAD
package com.woowacourse.thankoo.reservation.application.dto;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.COFFEE_COUPON_CONTENT;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.time;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.member.domain.Email;
import com.woowacourse.thankoo.member.domain.Name;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReservationMessage 는 ")
class ReservationMessageTest {

    @DisplayName("예약 상태가 변경되었을 때 알림 메시지를 생성한다. (승인)")
    @Test
    void createAccept() {
        Coupon coupon = new Coupon(1L, 2L, 3L, COFFEE_COUPON_CONTENT, CouponStatus.NOT_USED);
        Reservation reservation = Reservation.reserve(time(1L),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.ACCEPT,
                3L,
                coupon);

        Message message = ReservationMessage.updateOf(new Name(HOHO_NAME), new Email(HUNI_EMAIL), reservation);

        assertAll(
                () -> assertThat(message.getTitle()).contains("\uD83D\uDC7B 예약 요청에 응답이 왔어요."),
                () -> assertThat(message.getEmails()).containsExactly(HUNI_EMAIL),
                () -> assertThat(message.getContents()).contains(
                        "요청자 : " + HOHO_NAME, "쿠폰 : 호호의 카누쿠폰", "예약 상태 : 승인\uD83E\uDD70")
        );
    }

    @DisplayName("예약 상태가 변경되었을 때 알림 메시지를 생성한다. (거절)")
    @Test
    void createDeny() {
        Coupon coupon = new Coupon(1L, 2L, 3L, COFFEE_COUPON_CONTENT, CouponStatus.NOT_USED);
        Reservation reservation = Reservation.reserve(time(1L),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.DENY,
                3L,
                coupon);

        Message message = ReservationMessage.updateOf(new Name(HOHO_NAME), new Email(HUNI_EMAIL), reservation);

        assertAll(
                () -> assertThat(message.getTitle()).contains("\uD83D\uDC7B 예약 요청에 응답이 왔어요."),
                () -> assertThat(message.getEmails()).containsExactly(HUNI_EMAIL),
                () -> assertThat(message.getContents()).contains(
                        "요청자 : " + HOHO_NAME, "쿠폰 : 호호의 카누쿠폰", "예약 상태 : 거절\uD83D\uDE05")
        );
    }
}
=======
package com.woowacourse.thankoo.reservation.application.dto;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.COFFEE_COUPON_CONTENT;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.time;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.member.domain.Email;
import com.woowacourse.thankoo.member.domain.Name;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReservationMessage 는 ")
class ReservationMessageTest {

    @DisplayName("예약 상태가 변경되었을 때 알림 메시지를 생성한다. (승인)")
    @Test
    void createAccept() {
        Coupon coupon = new Coupon(1L, 2L, 3L, COFFEE_COUPON_CONTENT, CouponStatus.NOT_USED);
        Reservation reservation = Reservation.reserve(time(1L),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.ACCEPT,
                3L,
                coupon);

        Message message = ReservationMessage.updateOf(new Name(HOHO_NAME), new Email(HUNI_EMAIL), reservation);

        assertAll(
                () -> assertThat(message.getTitle()).contains("\uD83D\uDC7B 예약 요청에 응답이 왔어요."),
                () -> assertThat(message.getEmails()).containsExactly(HUNI_EMAIL),
                () -> assertThat(message.getContents()).contains(
                        "요청자 : " + HOHO_NAME, "쿠폰 : 호호의 카누쿠폰", "예약 상태 : 승인\uD83E\uDD70")
        );
    }

    @DisplayName("예약 상태가 변경되었을 때 알림 메시지를 생성한다. (거절)")
    @Test
    void createDeny() {
        Coupon coupon = new Coupon(1L, 2L, 3L, COFFEE_COUPON_CONTENT, CouponStatus.NOT_USED);
        Reservation reservation = Reservation.reserve(time(1L),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.DENY,
                3L,
                coupon);

        Message message = ReservationMessage.updateOf(new Name(HOHO_NAME), new Email(HUNI_EMAIL), reservation);

        assertAll(
                () -> assertThat(message.getTitle()).contains("\uD83D\uDC7B 예약 요청에 응답이 왔어요."),
                () -> assertThat(message.getEmails()).containsExactly(HUNI_EMAIL),
                () -> assertThat(message.getContents()).contains(
                        "요청자 : " + HOHO_NAME, "쿠폰 : 호호의 카누쿠폰", "예약 상태 : 거절\uD83D\uDE05")
        );
    }
}
>>>>>>> daba63a0244b6bacd428d6c3df98da76456f414a
