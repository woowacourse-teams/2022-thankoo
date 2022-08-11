package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.alarm.support.Alarm;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.alarm.AlarmMessage;
import com.woowacourse.thankoo.alarm.dto.AlarmMessageRequest;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
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

    @Alarm
    public Long save(final Long memberId, final ReservationRequest reservationRequest) {
        Coupon coupon = couponRepository.findById(reservationRequest.getCouponId())
                .orElseThrow(() -> new InvalidCouponException(ErrorType.NOT_FOUND_COUPON));
        Member foundMember = getMember(memberId);

        Reservation reservation = new Reservation(reservationRequest.getStartAt(),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.WAITING,
                foundMember.getId(),
                coupon);
        reservation.reserve();

        Reservation savedReservation = reservationRepository.save(reservation);
        sendAlarmMessage(coupon.getSenderId(), AlarmMessage.RECEIVE_RESERVATION);
        return savedReservation.getId();
    }

    @Alarm
    public void updateStatus(final Long memberId,
                             final Long reservationId,
                             final ReservationStatusRequest reservationStatusRequest) {
        Member foundMember = getMember(memberId);
        Reservation reservation = getReservationById(reservationId);
        ReservationStatus futureStatus = ReservationStatus.from(reservationStatusRequest.getStatus());
        reservation.update(foundMember, futureStatus, reservedMeetingCreator);
        sendAlarmMessage(reservation.getMemberId(), AlarmMessage.RESPONSE_RESERVATION);
    }

    public void cancel(final Long memberId,
                       final Long reservationId) {
        Member foundMember = getMember(memberId);
        Reservation reservation = getReservationById(reservationId);
        reservation.cancel(foundMember);
        sendAlarmMessage(foundMember.getId(), AlarmMessage.CANCEL_RESERVATION);
    }

    private void sendAlarmMessage(final Long memberId, final AlarmMessage message) {
        AlarmManager.setResources(new AlarmMessageRequest(getEmail(getMember(memberId)), message));
    }

    private Reservation getReservationById(final Long reservationId) {
        return reservationRepository.findWithCouponById(reservationId)
                .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private String getEmail(final Member member) {
        return member.getEmail().getValue();
    }
}
