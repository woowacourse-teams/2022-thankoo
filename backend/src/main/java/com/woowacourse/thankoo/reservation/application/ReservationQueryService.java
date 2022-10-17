package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.domain.ReservationCoupon;
import com.woowacourse.thankoo.reservation.domain.ReservationQueryRepository;
import com.woowacourse.thankoo.reservation.presentation.dto.SimpleReservationResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;
    private final MemberRepository memberRepository;

    public List<SimpleReservationResponse> getReceivedReservations(final Long memberId, final Long organizationId) {
        Member member = getMemberById(memberId);
        List<ReservationCoupon> receivedReservations = reservationQueryRepository.findReceivedReservations(
                member.getId(), organizationId, LocalDateTime.now());
        return toReservationResponses(receivedReservations);
    }

    public List<SimpleReservationResponse> getSentReservations(final Long memberId, final Long organizationId) {
        Member member = getMemberById(memberId);
        List<ReservationCoupon> sentReservations = reservationQueryRepository.findSentReservations(member.getId(),
                organizationId,
                LocalDateTime.now());
        return toReservationResponses(sentReservations);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private List<SimpleReservationResponse> toReservationResponses(final List<ReservationCoupon> reservationCoupons) {
        return reservationCoupons.stream()
                .map(SimpleReservationResponse::from)
                .collect(Collectors.toList());
    }
}
