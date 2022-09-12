package com.woowacourse.thankoo.admin.reservation.application;

import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationRequest;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationResponse;
import com.woowacourse.thankoo.admin.reservation.domain.AdminReservationQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminReservationService {

    private final AdminReservationQueryRepository reservationRepository;


    public List<AdminReservationResponse> getReservations(final AdminReservationRequest request) {
        // todo : 예외를 어떻게 할지에 대한 회의 필요
        return reservationRepository.findAll(request.getStart(), request.getEnd(), request.getStatus());
    }
}
