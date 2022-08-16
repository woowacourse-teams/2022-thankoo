package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.alarm.support.Alarm;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.alarm.support.AlarmMessageRequest;
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
import java.util.List;
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

        Reservation reservation = Reservation.reserve(reservationRequest.getStartAt(),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.WAITING,
                foundMember.getId(),
                coupon);

        Reservation savedReservation = reservationRepository.save(reservation);

        // todo : 리팩토링
        Member sender = getMember(coupon.getSenderId());
        String senderName = "요청자 : " + sender.getName().getValue();
        String date = "예약 요청일 : " + savedReservation.getTimeUnit().getDate().toString();
        String couponTitle = "쿠폰 : " + coupon.getCouponContent().getTitle();

        AlarmManager.setResources(
                new AlarmMessageRequest(getEmail(sender),
                        "예약 요청이 도착했어요.", List.of(senderName, date, couponTitle)));

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

        // todo : 리팩토링
        Member receiver = getMember(reservation.getCoupon().getReceiverId());
        String senderName = "요청자 : " + receiver.getName().getValue();
        String couponTitle = "쿠폰 : " + reservation.getCoupon().getCouponContent().getTitle();
        String status = "예약 상태 : " + reservation.getReservationStatus().toString();

        AlarmManager.setResources(
                new AlarmMessageRequest(receiver.getEmail().getValue(),
                        "예약 요청에 응답이 왔어요.", List.of(senderName, couponTitle, status)));
    }

    public void cancel(final Long memberId,
                       final Long reservationId) {
        Member foundMember = getMember(memberId);
        Reservation reservation = getReservationById(reservationId);
        reservation.cancel(foundMember);

        // todo : 리팩토링
        String senderName = "요청자 : " + getMember(reservation.getCoupon().getSenderId()).getName().getValue();
        String couponTitle = "쿠폰 : " + reservation.getCoupon().getCouponContent().getTitle();

        AlarmManager.setResources(
                new AlarmMessageRequest(getEmail(getMember(memberId)),
                        "예약 요청이 취소되었어요.", List.of(senderName, couponTitle)));
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
