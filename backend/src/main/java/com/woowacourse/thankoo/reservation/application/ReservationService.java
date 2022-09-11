package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.alarm.application.AlarmSender;
import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.dto.ReservationMessage;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    private final AlarmSender alarmSender;

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

        sendMessage(foundMember, coupon, savedReservation);
        return savedReservation.getId();
    }

    private void sendMessage(final Member member, final Coupon coupon, final Reservation reservation) {
        Member sender = getMember(coupon.getSenderId());
        Message message = ReservationMessage.of(member.getName(), sender.getEmail(),
                reservation.getTimeUnit().getDate(), coupon.getCouponContent());
        alarmSender.send(message);
    }

    public void updateStatus(final Long memberId,
                             final Long reservationId,
                             final ReservationStatusRequest reservationStatusRequest) {
        Member foundMember = getMember(memberId);
        Reservation reservation = getReservationById(reservationId);
        ReservationStatus futureStatus = ReservationStatus.from(reservationStatusRequest.getStatus());
        reservation.update(foundMember, futureStatus, reservedMeetingCreator);

        Member receiver = getMember(reservation.getCoupon().getReceiverId());
        alarmSender.send(ReservationMessage.updateOf(foundMember.getName(), receiver.getEmail(), reservation));

    }

    public void cancel(final Long memberId, final Long reservationId) {
        Member foundMember = getMember(memberId);
        Reservation reservation = getReservationById(reservationId);
        reservation.cancel(foundMember);

        Member sender = getMember(reservation.getCoupon().getSenderId());
        alarmSender.send(ReservationMessage.cancelOf(foundMember.getName(), sender.getEmail(), reservation));
    }

    private Reservation getReservationById(final Long reservationId) {
        return reservationRepository.findWithCouponById(reservationId)
                .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    public void cancel(final ReservationStatus reservationStatus, final LocalDateTime dateTime) {
        List<Reservation> reservations = reservationRepository.findAllByReservationStatusAndTimeUnitTime(
                reservationStatus, dateTime);

        reservationRepository.updateReservationStatus(ReservationStatus.CANCELED, getIds(reservations));
        couponRepository.updateCouponStatus(CouponStatus.NOT_USED, getCouponIds(reservations));
    }

    private List<Long> getIds(final List<Reservation> reservations) {
        return reservations.stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
    }

    private List<Long> getCouponIds(final List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> reservation.getCoupon().getId())
                .collect(Collectors.toList());
    }
}
