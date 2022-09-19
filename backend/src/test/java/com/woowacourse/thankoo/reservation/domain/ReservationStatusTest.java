package com.woowacourse.thankoo.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("ReservationStatus 는 ")
class ReservationStatusTest {

    @Nested
    @DisplayName("값을 통해 찾을 때 ")
    class FromTest {

        @DisplayName("올바른 ReservationStatus 를 반환한다.")
        @ParameterizedTest
        @CsvSource(value = {"accept:ACCEPT", "deny:DENY", "waiting:WAITING"}, delimiter = ':')
        void from(String value, ReservationStatus reservationStatus) {
            assertThat(ReservationStatus.from(value)).isEqualTo(reservationStatus);
        }

        @DisplayName("올바른 ReservationStatus 를 반환한다.")
        @Test
        void fromException() {
            assertThatThrownBy(() -> ReservationStatus.from("abc"))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("존재하지 않는 예약 상태입니다.");
        }
    }

    @DisplayName("WAITING 인지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"WAITING:true", "DENY:false", "ACCEPT:false"}, delimiter = ':')
    void isWaiting(ReservationStatus reservationStatus, boolean isWaiting) {
        assertThat(reservationStatus.isWaiting()).isEqualTo(isWaiting);
    }

    @DisplayName("DENY 인지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"WAITING:false", "DENY:true", "ACCEPT:false"}, delimiter = ':')
    void isDeny(ReservationStatus reservationStatus, boolean isDeny) {
        assertThat(reservationStatus.isDeny()).isEqualTo(isDeny);
    }

    @DisplayName("CANCELED 인지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"WAITING:false", "DENY:false", "ACCEPT:false", "CANCELED:true"}, delimiter = ':')
    void isCanceled(ReservationStatus reservationStatus, boolean isCanceled) {
        assertThat(reservationStatus.isCanceled()).isEqualTo(isCanceled);
    }
}
