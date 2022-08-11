package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.coupon.domain.CouponContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ContentRequest {

    private String couponType;
    private String title;
    private String message;

    public ContentRequest(final String couponType, final String title, final String message) {
        this.couponType = couponType;
        this.title = title;
        this.message = message;
    }

    public CouponContent toEntity() {
        return new CouponContent(couponType, title, message);
    }

    @Override
    public String toString() {
        return "ContentRequest{" +
                "couponType='" + couponType + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
