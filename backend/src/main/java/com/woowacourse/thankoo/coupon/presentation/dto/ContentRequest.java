package com.woowacourse.thankoo.coupon.presentation.dto;

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
}
