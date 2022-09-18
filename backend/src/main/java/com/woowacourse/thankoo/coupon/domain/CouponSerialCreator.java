package com.woowacourse.thankoo.coupon.domain;

public interface CouponSerialCreator {

    Coupon create(Long receiverId, final String serialCode);
}
