package com.woowacourse.thankoo.reservation.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMemberId(Long memberId);
}
