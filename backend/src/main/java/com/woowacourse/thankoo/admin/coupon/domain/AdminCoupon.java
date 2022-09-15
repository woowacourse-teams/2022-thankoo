package com.woowacourse.thankoo.admin.coupon.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AdminCoupon {

    private final Long couponId;
    private final Member sender;
    private final Member receiver;
    private final String couponType;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public AdminCoupon(final Long couponId,
                       final Member sender,
                       final Member receiver,
                       final String couponType,
                       final String status,
                       final LocalDateTime createdAt,
                       final LocalDateTime modifiedAt) {
        this.couponId = couponId;
        this.sender = sender;
        this.receiver = receiver;
        this.couponType = couponType;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
