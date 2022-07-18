package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meeting_date", nullable = false)
    private LocalDate meetingDate;

    @Column(name = "meeting_time", nullable = false)
    private LocalDateTime meetingTime;

    @Column(name = "time_zone", length = 20, nullable = false)
    private String timeZone;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    public Reservation(final Long id,
                       final LocalDate meetingDate,
                       final LocalDateTime meetingTime,
                       final TimeZoneType timeZone,
                       final ReservationStatus reservationStatus,
                       final Long couponId) {
        this.id = id;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.timeZone = timeZone.getId();
        this.reservationStatus = reservationStatus;
        this.couponId = couponId;
    }

    public Reservation(final LocalDate meetingDate,
                       final LocalDateTime meetingTime,
                       final TimeZoneType timeZone,
                       final ReservationStatus reservationStatus,
                       final Long couponId) {
        this(null, meetingDate, meetingTime, timeZone, reservationStatus, couponId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", meetingDate=" + meetingDate +
                ", meetingTime=" + meetingTime +
                ", TimeZone='" + timeZone +
                ", reservationStatus=" + reservationStatus +
                ", couponId=" + couponId +
                '}';
    }
}
