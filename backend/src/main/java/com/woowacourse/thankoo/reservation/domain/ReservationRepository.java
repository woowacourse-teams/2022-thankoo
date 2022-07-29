package com.woowacourse.thankoo.reservation.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = "coupon", type = EntityGraphType.LOAD)
    Optional<Reservation> findWithCouponById(Long reservationId);

    Optional<Reservation> findTopByCouponIdAndReservationStatus(Long couponId, ReservationStatus status);
}
