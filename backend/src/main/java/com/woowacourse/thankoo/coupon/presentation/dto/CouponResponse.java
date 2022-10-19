package com.woowacourse.thankoo.coupon.presentation.dto;

import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.time.LocalDate;
import java.util.Locale;
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
    private String status;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    private CouponResponse(final Long couponId,
                           final MemberResponse sender,
                           final MemberResponse receiver,
                           final CouponContentResponse content,
                           final String status,
                           final LocalDate createdDate,
                           final LocalDate modifiedDate) {
        this.couponId = couponId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.status = status.toLowerCase(Locale.ROOT);
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static CouponResponse of(final MemberCoupon memberCoupon) {
        return new CouponResponse(memberCoupon.getCouponId(),
                MemberResponse.of(memberCoupon.getSender()),
                MemberResponse.of(memberCoupon.getReceiver()),
                CouponContentResponse.from(memberCoupon.getCouponType(),
                        memberCoupon.getTitle(),
                        memberCoupon.getMessage()),
                memberCoupon.getStatus(),
                memberCoupon.getCreatedDate(),
                memberCoupon.getModifiedDate()
        );
    }

    @Override
    public String toString() {
        return "CouponResponse{" +
                "couponId=" + couponId +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content=" + content +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
