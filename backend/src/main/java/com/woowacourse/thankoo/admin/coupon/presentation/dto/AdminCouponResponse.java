package com.woowacourse.thankoo.admin.coupon.presentation.dto;

import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import lombok.Getter;

@Getter
public class AdminCouponResponse {

    private final Long couponId;
    private final AdminMemberResponse sender;
    private final AdminMemberResponse receiver;
    private final String type;
    private final String title;
    private final String message;
    private final String status;

    public AdminCouponResponse(final Long couponId,
                               final AdminMemberResponse sender,
                               final AdminMemberResponse receiver,
                               final String type,
                               final String title,
                               final String message,
                               final String status) {
        this.couponId = couponId;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public static AdminCouponResponse of(final MemberCoupon memberCoupon) {
        return new AdminCouponResponse(
                memberCoupon.getCouponId(),
                AdminMemberResponse.of(memberCoupon.getSender()),
                AdminMemberResponse.of(memberCoupon.getReceiver()),
                memberCoupon.getCouponType(),
                memberCoupon.getTitle(),
                memberCoupon.getMessage(),
                memberCoupon.getStatus()
        );
    }
}
