package com.woowacourse.thankoo.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Reservation 는 ")
class ReservationTest {

    @DisplayName("일정 요청을 할 때")
    @Nested
    class MeetingValidationTest {

        @DisplayName("일정 요청이 가능한 기간인면 통과한다.")
        @Test
        void isValidMeetingTime() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);

            assertThatNoException()
                    .isThrownBy(
                            () -> new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, 1L)
                    );
        }

        @DisplayName("일정 요청이 불가능한 기간이면 예외가 발생한다.")
        @Test
        void invalidMeetingTime() {
            LocalDateTime futureDate = LocalDateTime.now().minusDays(1L);

            assertThatThrownBy(
                    () -> new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, 1L))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("유효하지 않은 일정입니다.");
        }
    }
}
