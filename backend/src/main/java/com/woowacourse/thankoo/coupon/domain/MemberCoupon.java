package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberCoupon {

    private final Long couponId;
    private final Member sender;
    private final Member receiver;
    private final String couponType;
    private final String title;
    private final String message;

    public MemberCoupon(final Long couponId,
                        final Member sender,
                        final Member receiver,
                        final String couponType,
                        final String title,
                        final String message) {
        this.couponId = couponId;
        this.sender = sender;
        this.receiver = receiver;
        this.couponType = couponType;
        this.title = title;
        this.message = message;
    }
}
