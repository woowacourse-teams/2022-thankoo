package com.woowacourse.thankoo.admin.coupon.infrastructure;

import com.woowacourse.thankoo.admin.reservation.domain.AdminReservationRepository;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class AdminCouponReservationService implements AdminReservationProvider {

    private final AdminReservationRepository adminReservationRepository;

    @Override
    public void cancelReservation(final List<Coupon> coupons) {
        adminReservationRepository.updateReservationStatus(
                ReservationStatus.WAITING, ReservationStatus.CANCELED, coupons);
    }
}
