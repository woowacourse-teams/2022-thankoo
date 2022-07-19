package com.woowacourse.thankoo.reservation.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.reservation.application.ReservationService;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PresentationController {

    private final ReservationService reservationService;

    @PostMapping("/api/reservations")
    public ResponseEntity<Void> reserve(@AuthenticationPrincipal final Long memberId,
                                        @RequestBody final ReservationRequest reservationRequest) {
        Long reserveId = reservationService.reserve(memberId, reservationRequest);
        return ResponseEntity.created(URI.create("/api/reservations/" + reserveId)).build();
    }
}
