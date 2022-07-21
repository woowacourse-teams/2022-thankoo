package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.reservation.domain.ReservationCoupon;
import com.woowacourse.thankoo.reservation.domain.ReservationQueryRepository;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;

    public List<ReservationResponse> getReceivedReservations(final Long memberId) {
        List<ReservationCoupon> receivedReservations = reservationQueryRepository.findReceivedReservations(memberId,
                LocalDateTime.now());
        return toReservationResponses(receivedReservations);
    }

    public List<ReservationResponse> getSentReservations(final Long memberId) {
        List<ReservationCoupon> receivedReservations = reservationQueryRepository.findSentReservations(memberId,
                LocalDateTime.now());
        return toReservationResponses(receivedReservations);
    }

    private List<ReservationResponse> toReservationResponses(List<ReservationCoupon> receivedReservations) {
        return receivedReservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }
}
