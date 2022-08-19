package com.woowacourse.thankoo.common.fake;

import com.woowacourse.thankoo.common.domain.TimeUnit;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;

public class FakeReservedMeetingCreator implements ReservedMeetingCreator {

    @Override
    public void create(final Coupon coupon, final TimeUnit timeUnit) {
        if (coupon == null || timeUnit == null) {
            throw new IllegalArgumentException();
        }
    }
}
