package com.woowacourse.thankoo.common.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TimeUnit {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "time_zone", length = 20, nullable = false)
    private String timeZone;

    public TimeUnit(final LocalDate date, final LocalDateTime time, final String timeZone) {
        validateEqualDate(date, time);
        this.date = date;
        this.time = time;
        this.timeZone = timeZone;
    }

    private void validateEqualDate(final LocalDate meetingDate, final LocalDateTime meetingTime) {
        if (!meetingDate.isEqual(meetingTime.toLocalDate())) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_TIME);
        }
    }

    public boolean isAfterNow() {
        return time.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeUnit)) {
            return false;
        }
        TimeUnit that = (TimeUnit) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time)
                && Objects.equals(timeZone, that.timeZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, timeZone);
    }

    @Override
    public String toString() {
        return "TimeUnit{" +
                "date=" + date +
                ", time=" + time +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
