package com.woowacourse.thankoo.admin.reservation.presentation;

import com.woowacourse.thankoo.admin.reservation.application.AdminReservationService;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationRequest;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    private final AdminReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<AdminReservationResponse>> getReservations(
            final AdminReservationRequest reservationRequest) {
        return ResponseEntity.ok().body(reservationService.getReservations(reservationRequest));
    }
}
