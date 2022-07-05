package com.woowacourse.thankoo.coupon.presentation.dto;

import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponHistoryResponse {

    private Long couponHistoryId;
    private MemberResponse sender;
    private MemberResponse receiver;
    private CouponContentResponse content;

    public CouponHistoryResponse(final Long couponHistoryId,
                                 final MemberResponse sender,
                                 final MemberResponse receiver,
                                 final CouponContentResponse content) {
        this.couponHistoryId = couponHistoryId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
