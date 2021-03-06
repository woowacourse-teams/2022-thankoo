package com.woowacourse.thankoo.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReservationTime 은 ")
class ReservationTimeTest {

    @DisplayName("예약 시간이 오늘보다 이전인 경우 예외가 발생한다.")
    @Test
    void validateMeetingTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1L);
        assertThatThrownBy(
                () -> new ReservationTime(localDateTime.toLocalDate(), localDateTime, TimeZoneType.ASIA_SEOUL.getId()))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("유효하지 않은 일정입니다.");
    }

    @DisplayName("예약 시간과 날짜가 동일하지 않을 경우 예외가 발생한다.")
    @Test
    void validateMeetingDateEquals() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1L);
        LocalDate localDate = LocalDate.now().plusDays(2L);

        assertThatThrownBy(() -> new ReservationTime(localDate, localDateTime, TimeZoneType.ASIA_SEOUL.getId()))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("유효하지 않은 일정입니다.");
    }
}
