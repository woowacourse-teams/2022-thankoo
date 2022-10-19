package com.woowacourse.thankoo.coupon.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;


    private CouponResponse(final Long couponId,
                           final MemberResponse sender,
                           final MemberResponse receiver,
                           final CouponContentResponse content,
                           final String status,
                           final LocalDate createdDate,
                           final LocalDateTime modifiedDateTime) {
        this.couponId = couponId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.status = status.toLowerCase(Locale.ROOT);
        this.createdDate = createdDate;
        this.modifiedDateTime = modifiedDateTime;
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
                memberCoupon.getModifiedDateTime()
        );
    }

    @Override
    public String toString() {
        return "CouponResponse{" +
                "couponId=" + couponId +
                ", sender=" + sender.getId() +
                ", receiver=" + receiver.getId() +
                ", content=" + content +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDateTime=" + modifiedDateTime +
                '}';
    }
}
