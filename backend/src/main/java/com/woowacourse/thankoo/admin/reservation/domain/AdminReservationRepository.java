package com.woowacourse.thankoo.admin.reservation.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reservation r SET r.reservationStatus = :updatingStatus WHERE r.reservationStatus = :status AND r.coupon IN (:coupons)")
    void updateReservationStatus(@Param("status") ReservationStatus status,
                                 @Param("updatingStatus") ReservationStatus updatingStatus,
                                 @Param("coupons") List<Coupon> coupons);
}
