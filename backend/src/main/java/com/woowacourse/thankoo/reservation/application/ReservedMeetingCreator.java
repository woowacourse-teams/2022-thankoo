package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.common.domain.TimeUnit;
import com.woowacourse.thankoo.coupon.domain.Coupon;

public interface ReservedMeetingCreator {

    void create(Coupon coupon, TimeUnit timeUnit);
}
