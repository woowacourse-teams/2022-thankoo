package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.meeting.domain.MeetingTime;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

    @Embedded
    private MeetingTime meetingTime;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    public Reservation(final Long id,
                       final MeetingTime meetingTime,
                       final ReservationStatus reservationStatus,
                       final Long memberId,
                       final Coupon coupon) {
        validateReservationMember(memberId, coupon);
        validateCouponStatus(coupon);
        this.id = id;
        this.meetingTime = meetingTime;
        this.reservationStatus = reservationStatus;
        this.memberId = memberId;
        this.coupon = coupon;
    }

    public Reservation(final LocalDateTime meetingTime,
                       final TimeZoneType timeZone,
                       final ReservationStatus reservationStatus,
                       final Long memberId,
                       final Coupon coupon) {
        this(null,
                new MeetingTime(meetingTime.toLocalDate(), meetingTime, timeZone.getId()),
                reservationStatus,
                memberId,
                coupon);
    }

    private void validateReservationMember(final Long memberId, final Coupon coupon) {
        if (!coupon.isReceiver(memberId)) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_MEMBER_MISMATCH);
        }
    }

    private void validateCouponStatus(final Coupon coupon) {
        if (!coupon.isNotUsed()) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_COUPON_STATUS);
        }
    }

    public void reserve() {
        coupon.reserve();
    }

    public void updateStatus(final Member member,
                             final String status,
                             final ReservedMeetingCreator reservedMeetingCreator) {
        ReservationStatus updateReservationStatus = ReservationStatus.from(status);
        validateReservation(member, updateReservationStatus);
        validateCouponStatus();

        reservationStatus = updateReservationStatus;
        if (reservationStatus.isDeny()) {
            coupon.denied();
            return;
        }
        coupon.accepted();
        reservedMeetingCreator.create(this);
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
    public boolean equals(final Object o) {
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
                ", reservationTime=" + meetingTime +
                ", reservationStatus=" + reservationStatus +
                ", memberId=" + memberId +
                ", couponId=" + coupon.getId() +
                '}';
    }
}
