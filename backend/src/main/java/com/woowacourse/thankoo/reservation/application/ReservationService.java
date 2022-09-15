package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.Reservations;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final ReservedMeetingCreator reservedMeetingCreator;

    public Long save(final Long memberId, final ReservationRequest reservationRequest) {
        Coupon coupon = couponRepository.findById(reservationRequest.getCouponId())
                .orElseThrow(() -> new InvalidCouponException(ErrorType.NOT_FOUND_COUPON));
        Member foundMember = getMember(memberId);

        Reservation reservation = Reservation.reserve(reservationRequest.getStartAt(),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.WAITING,
                foundMember.getId(),
                coupon);

        Reservation savedReservation = reservationRepository.save(reservation);
        return savedReservation.getId();
    }

    public void updateStatus(final Long memberId,
                             final Long reservationId,
                             final ReservationStatusRequest reservationStatusRequest) {
        Member foundMember = getMember(memberId);
        Reservation reservation = getReservationById(reservationId);
        ReservationStatus futureStatus = ReservationStatus.from(reservationStatusRequest.getStatus());
        reservation.update(foundMember, futureStatus, reservedMeetingCreator);
    }

    public void cancel(final Long memberId, final Long reservationId) {
        Member foundMember = getMember(memberId);
        Reservation reservation = getReservationById(reservationId);
        reservation.cancel(foundMember);
    }

    private Reservation getReservationById(final Long reservationId) {
        return reservationRepository.findWithCouponById(reservationId)
                .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    public void cancelExpiredReservation(final LocalDateTime dateTime) {
        Reservations reservations = new Reservations(reservationRepository.findAllByReservationStatusAndTimeUnitTime(
                ReservationStatus.WAITING, dateTime));

        reservationRepository.updateReservationStatus(ReservationStatus.CANCELED, reservations.getIds());
        couponRepository.updateCouponStatus(CouponStatus.NOT_USED, reservations.getCouponIds());
    }
}
