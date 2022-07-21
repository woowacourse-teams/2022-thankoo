package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    public Reservation(final Long id,
                       final LocalDate meetingDate,
                       final LocalDateTime meetingTime,
                       final TimeZoneType timeZone,
                       final ReservationStatus reservationStatus,
                       final Long memberId,
                       final Coupon coupon) {
        validateReservationMember(memberId, coupon);
        validateMeetingTime(meetingTime);
        validateCouponStatus(coupon);
        this.id = id;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.timeZone = timeZone.getId();
        this.reservationStatus = reservationStatus;
        this.memberId = memberId;
        this.coupon = coupon;
    }

    private void validateReservationMember(final Long memberId, final Coupon coupon) {
        if (!coupon.isReceiver(memberId)) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_MEMBER_MISMATCH);
        }
    }

    private void validateMeetingTime(final LocalDateTime meetingTime) {
        if (LocalDateTime.now().isAfter(meetingTime)) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_MEETING_TIME);
        }
    }

    private void validateCouponStatus(final Coupon coupon) {
        if (!coupon.isNotUsed()) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_COUPON_STATUS);
        }
    }

    public Reservation(final LocalDateTime meetingTime,
                       final TimeZoneType timeZone,
                       final ReservationStatus reservationStatus,
                       final Long memberId,
                       final Coupon coupon) {
        this(null, meetingTime.toLocalDate(), meetingTime, timeZone, reservationStatus, memberId, coupon);
    }

    public void reserve() {
        coupon.reserve();
    }

    public void updateStatus(final Member member, final String status) {
        ReservationStatus updateReservationStatus = ReservationStatus.from(status);
        validateReservation(member, updateReservationStatus);
        validateCouponStatus();

        reservationStatus = updateReservationStatus;
        if (reservationStatus.isDeny()) {
            coupon.denied();
            return;
        }
        coupon.accepted();
    }

    private void validateReservation(final Member member, final ReservationStatus updateReservationStatus) {
        if (isInsufficientReservationStatus(member.getId(), updateReservationStatus)) {
            throw new InvalidReservationException(ErrorType.CAN_NOT_CHANGE_RESERVATION_STATUS);
        }
    }

    private boolean isInsufficientReservationStatus(final Long memberId,
                                                    final ReservationStatus updateReservationStatus) {
        return !this.reservationStatus.isWaiting()
                || updateReservationStatus.isWaiting()
                || !coupon.isSender(memberId);
    }

    private void validateCouponStatus() {
        if (!coupon.canAcceptReservation()) {
            throw new InvalidReservationException(ErrorType.CAN_NOT_CHANGE_RESERVATION_STATUS);
        }
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
                ", coupon=" + coupon +
                '}';
    }
}
