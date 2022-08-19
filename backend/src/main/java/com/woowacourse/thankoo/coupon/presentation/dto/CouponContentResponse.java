package com.woowacourse.thankoo.coupon.presentation.dto;

import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponContentResponse {

    private String couponType;
    private String title;
    private String message;

    private CouponContentResponse(final String couponType, final String title, final String message) {
        this.couponType = couponType.toLowerCase(Locale.ROOT);
        this.title = title;
        this.message = message;
    }

    public static CouponContentResponse from(final String couponType, final String title, final String message) {
        return new CouponContentResponse(couponType, title, message);
    }

    @Override
    public String toString() {
        return "CouponContentResponse{" +
                "couponType='" + couponType + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
