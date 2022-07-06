package com.woowacourse.thankoo.coupon.exception;

public class InvalidCouponTypeException extends RuntimeException {

    public InvalidCouponTypeException() {
        super("존재하지 않는 쿠폰 타입입니다.");
    }
}
