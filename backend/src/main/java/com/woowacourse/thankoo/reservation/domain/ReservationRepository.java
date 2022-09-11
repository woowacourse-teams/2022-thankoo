package com.woowacourse.thankoo.reservation.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = "coupon", type = EntityGraphType.LOAD)
    Optional<Reservation> findWithCouponById(Long reservationId);

    Optional<Reservation> findTopByCouponIdAndReservationStatus(Long couponId, ReservationStatus status);

    List<Reservation> findAllByReservationStatusAndTimeUnit_Time(ReservationStatus status, LocalDateTime date);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reservation r SET r.reservationStatus = :status WHERE r.id IN (:reservationIds)")
    void updateReservationStatus(@Param("status") ReservationStatus status,
                                 @Param("reservationIds") List<Long> reservationIds);
}
