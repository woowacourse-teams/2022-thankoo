package com.woowacourse.thankoo.reservation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

//    @Query("select r from Reservation r join fetch r.coupon c where ")
//    @EntityGraph(value = "Reservation.coupon")
//    List<Reservation> findWithCouponReceiverId(Long receiverId);
}
