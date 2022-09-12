package com.woowacourse.thankoo.admin.reservation.domain;

import com.woowacourse.thankoo.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminReservationRepository extends JpaRepository<Reservation, Long> {

}
