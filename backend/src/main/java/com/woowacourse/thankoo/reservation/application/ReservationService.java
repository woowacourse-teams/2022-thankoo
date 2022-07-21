package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long memberId, ReservationRequest reservationRequest) {
        Coupon coupon = couponRepository.findById(reservationRequest.getCouponId())
                .orElseThrow(() -> new InvalidCouponException(ErrorType.NOT_FOUND_COUPON));
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));

        Reservation reservation = new Reservation(reservationRequest.getStartAt(),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.WAITING,
                foundMember.getId(),
                coupon);
        reservation.reserve();

        return reservationRepository.save(reservation).getId();
    }

    public List<ReservationResponse> getReservations(Long memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));

        return reservationRepository.findByMemberId(memberId).stream()
                .map(reservation -> ReservationResponse.from(reservation, foundMember))
                .collect(Collectors.toList());
    }
}
