package com.woowacourse.thankoo.admin.coupon.infrastructure;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import java.util.List;

public interface AdminReservationProvider {

    void cancelReservation(List<Coupon> coupons);
}
