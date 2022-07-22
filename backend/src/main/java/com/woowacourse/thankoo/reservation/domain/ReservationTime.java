package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationTime {

    @Column(name = "meeting_date", nullable = false)
    private LocalDate meetingDate;

    @Column(name = "meeting_time", nullable = false)
    private LocalDateTime meetingTime;

    @Column(name = "time_zone", length = 20, nullable = false)
    private String timeZone;

    public ReservationTime(final LocalDate meetingDate, final LocalDateTime meetingTime, final String timeZone) {
        validateMeetingTime(meetingTime);
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.timeZone = timeZone;
    }

    private void validateMeetingTime(final LocalDateTime meetingTime) {
        if (LocalDateTime.now().isAfter(meetingTime)) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_MEETING_TIME);
        }
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "meetingDate=" + meetingDate +
                ", meetingTime=" + meetingTime +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
