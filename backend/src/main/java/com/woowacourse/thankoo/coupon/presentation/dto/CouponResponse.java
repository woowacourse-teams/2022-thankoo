package com.woowacourse.thankoo.coupon.presentation.dto;

import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponResponse {

    private Long couponId;
    private MemberResponse sender;
    private MemberResponse receiver;
    private CouponContentResponse content;

    private CouponResponse(final Long couponId,
                           final MemberResponse sender,
                           final MemberResponse receiver,
                           final CouponContentResponse content) {
        this.couponId = couponId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public static CouponResponse of(final MemberCoupon memberCoupon) {
        return new CouponResponse(memberCoupon.getCouponId(),
                MemberResponse.of(memberCoupon.getSender()),
                MemberResponse.of(memberCoupon.getReceiver()),
                CouponContentResponse.from(memberCoupon.getCouponType(),
                        memberCoupon.getTitle(),
                        memberCoupon.getMessage())
        );
    }
}
