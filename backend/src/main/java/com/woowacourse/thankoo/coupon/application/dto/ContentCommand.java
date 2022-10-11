package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.coupon.domain.CouponContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ContentCommand {

    private String couponType;
    private String title;
    private String message;

    public ContentCommand(final String couponType, final String title, final String message) {
        this.couponType = couponType;
        this.title = title;
        this.message = message;
    }

    public CouponContent toEntity() {
        return new CouponContent(couponType, title, message);
    }

    @Override
    public String toString() {
        return "ContentCommand{" +
                "couponType='" + couponType + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
