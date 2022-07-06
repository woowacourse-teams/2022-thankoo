package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.coupon.domain.CouponHistory;
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

    public CouponHistory toEntity(final Long senderId) {
        return new CouponHistory(senderId,
                receiverId,
                content.getCouponType(),
                content.getTitle(),
                content.getMessage());
    }
}
