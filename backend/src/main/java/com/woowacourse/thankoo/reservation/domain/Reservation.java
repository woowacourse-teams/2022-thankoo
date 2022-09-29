package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.domain.TimeUnit;
import com.woowacourse.thankoo.common.event.Events;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.exception.ForbiddenException;
import com.woowacourse.thankoo.coupon.domain.Coupon;
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
    private TimeUnit timeUnit;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    private Reservation(final Long id,
                        final TimeUnit timeUnit,
                        final ReservationStatus reservationStatus,
                        final Long memberId,
                        final Coupon coupon) {
        validateRightTime(timeUnit);
        validateReservationMember(memberId, coupon);
        validateCouponStatus(coupon);
        this.id = id;
        this.timeUnit = timeUnit;
        this.reservationStatus = reservationStatus;
        this.memberId = memberId;
        this.coupon = coupon;
    }

    private Reservation(final LocalDateTime meetingTime,
                        final TimeZoneType timeZone,
                        final ReservationStatus reservationStatus,
                        final Long memberId,
                        final Coupon coupon) {
        this(null,
                new TimeUnit(meetingTime.toLocalDate(), meetingTime, timeZone.getId()),
                reservationStatus,
                memberId,
                coupon);
    }

    private void validateRightTime(final TimeUnit timeUnit) {
        if (!timeUnit.isAfterNow()) {
            throw new InvalidReservationException(ErrorType.INVALID_RESERVATION_TIME);
        }
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

    public static Reservation reserve(final LocalDateTime meetingTime,
                                      final TimeZoneType timeZone,
                                      final ReservationStatus reservationStatus,
                                      final Long memberId,
                                      final Coupon coupon) {
        Reservation reservation = new Reservation(meetingTime, timeZone, reservationStatus, memberId, coupon);
        reservation.reserve();
        Events.publish(ReservationSentEvent.from(reservation));
        return reservation;
    }

    private void reserve() {
        coupon.reserve();
    }

    public void update(final Member member,
                       final ReservationStatus futureStatus,
                       final ReservedMeetingCreator reservedMeetingCreator) {
        validateReservation(member, futureStatus);
        validateCouponStatus();

        reservationStatus = futureStatus;
        Events.publish(ReservationRepliedEvent.of(memberId, coupon, reservationStatus));
        if (reservationStatus.isDeny()) {
            coupon.rollBack();
            return;
        }
        coupon.accepted();
        reservedMeetingCreator.create(coupon, timeUnit);
    }

    private void validateReservation(final Member member, final ReservationStatus futureStatus) {
        if (isInsufficientReservationStatus(member.getId(), futureStatus)) {
            throw new InvalidReservationException(ErrorType.CAN_NOT_CHANGE_RESERVATION_STATUS);
        }
    }

    private boolean isInsufficientReservationStatus(final Long memberId,
                                                    final ReservationStatus futureStatus) {
        return !this.reservationStatus.isWaiting()
                || futureStatus.isWaiting()
                || futureStatus.isCanceled()
                || !coupon.isSender(memberId);
    }

    private void validateCouponStatus() {
        if (!coupon.canAcceptReservation()) {
            throw new InvalidReservationException(ErrorType.CAN_NOT_CHANGE_RESERVATION_STATUS);
        }
    }

    public void cancel(final Member member) {
        if (!member.isSameId(memberId)) {
            throw new ForbiddenException(ErrorType.FORBIDDEN);
        }
        if (!reservationStatus.isWaiting()) {
            throw new InvalidReservationException(ErrorType.CAN_NOT_CHANGE_RESERVATION_STATUS);
        }

        coupon.rollBack();
        reservationStatus = ReservationStatus.CANCELED;
        Events.publish(ReservationCanceledEvent.of(coupon, memberId));
    }

    public void complete() {
        reservationStatus = ReservationStatus.ACCEPT;
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
                ", reservationTime=" + timeUnit +
                ", reservationStatus=" + reservationStatus +
                ", memberId=" + memberId +
                ", couponId=" + coupon.getId() +
                '}';
    }
}
