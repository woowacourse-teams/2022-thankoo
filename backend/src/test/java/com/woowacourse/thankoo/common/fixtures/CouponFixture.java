package com.woowacourse.thankoo.common.fixtures;

import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;

import com.woowacourse.thankoo.coupon.domain.CouponContent;

public class CouponFixture {

    public static final String TYPE = "coffee";
    public static final String TITLE = "호호의 카누쿠폰";
    public static final String MESSAGE = "커피 타 줄게요";

    public static final String NOT_USED = "not-used";
    public static final String USED = "used";
    public static final String ALL = "all";

    public static final String TITLE_OVER = "abcdefghijklmnopqrstu";
    public static final String MESSAGE_OVER = "abcdefghijklmnopqrstabcdefghijklmnopqrst12345678901abcdefghijklmnopqrstabcdefghijklmnopqrst1234567890";

    public static final CouponContent COFFEE_COUPON_CONTENT = new CouponContent(COFFEE, TITLE, MESSAGE);
    public static final CouponContent MEAL_COUPON_CONTENT = new CouponContent(MEAL, "호호의 식사쿠폰", "밥먹어요");
}
