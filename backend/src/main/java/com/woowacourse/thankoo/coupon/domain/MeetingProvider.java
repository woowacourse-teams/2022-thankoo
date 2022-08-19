package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.MeetingResponse;

public interface MeetingProvider {

    MeetingResponse getMeetingByCouponId(Long couponId);
}
