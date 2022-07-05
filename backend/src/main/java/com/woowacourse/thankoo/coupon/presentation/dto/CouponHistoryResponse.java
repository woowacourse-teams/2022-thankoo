package com.woowacourse.thankoo.coupon.presentation.dto;

import com.woowacourse.thankoo.coupon.domain.MemberCouponHistory;
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

    private CouponHistoryResponse(final Long couponHistoryId,
                                  final MemberResponse sender,
                                  final MemberResponse receiver,
                                  final CouponContentResponse content) {
        this.couponHistoryId = couponHistoryId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public static CouponHistoryResponse of(final MemberCouponHistory memberCouponHistory) {
        return new CouponHistoryResponse(memberCouponHistory.getCouponHistoryId(),
                MemberResponse.of(memberCouponHistory.getSender()),
                MemberResponse.of(memberCouponHistory.getReceiver()),
                CouponContentResponse.from(memberCouponHistory.getCouponType(),
                        memberCouponHistory.getTitle(),
                        memberCouponHistory.getMessage())
        );
    }
}
