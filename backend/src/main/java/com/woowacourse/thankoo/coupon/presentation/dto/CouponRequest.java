package com.woowacourse.thankoo.coupon.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponRequest {

    private Long receiverId;
    private ContentRequest content;

    public CouponRequest(final Long receiverId, final ContentRequest content) {
        this.receiverId = receiverId;
        this.content = content;
    }
}
