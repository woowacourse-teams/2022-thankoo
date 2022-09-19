package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;

public interface CouponSerialCreator {

    Coupon create(Long receiverId, final String serialCode);
}
