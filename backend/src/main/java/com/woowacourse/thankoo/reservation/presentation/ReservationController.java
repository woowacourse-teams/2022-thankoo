package com.woowacourse.thankoo.reservation.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.reservation.application.ReservationService;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> reserve(@AuthenticationPrincipal final Long memberId,
                                        @RequestBody final ReservationRequest reservationRequest) {
        Long reserveId = reservationService.save(memberId, reservationRequest);
        return ResponseEntity.created(URI.create("/api/reservations/" + reserveId)).build();
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Void> updateStatus(@AuthenticationPrincipal final Long memberId,
                                             @PathVariable final Long reservationId,
                                             @RequestBody final ReservationStatusRequest reservationStatusRequest) {
        reservationService.updateStatus(memberId, reservationId, reservationStatusRequest);
        return ResponseEntity.noContent().build();
    }
}
