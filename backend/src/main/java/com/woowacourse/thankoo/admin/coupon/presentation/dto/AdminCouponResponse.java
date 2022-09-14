package com.woowacourse.thankoo.admin.coupon.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCoupon;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AdminCouponResponse {

    private final Long couponId;
    private final String type;
    private final String status;
    private final AdminMemberResponse sender;
    private final AdminMemberResponse receiver;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    public AdminCouponResponse(final Long couponId,
                               final String type,
                               final String status,
                               final AdminMemberResponse sender,
                               final AdminMemberResponse receiver,
                               final LocalDateTime createdAt,
                               final LocalDateTime modifiedAt) {
        this.couponId = couponId;
        this.type = type;
        this.status = status;
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static AdminCouponResponse of(final AdminCoupon coupon) {
        return new AdminCouponResponse(
                coupon.getCouponId(),
                coupon.getCouponType(),
                coupon.getStatus(),
                AdminMemberResponse.of(coupon.getSender()),
                AdminMemberResponse.of(coupon.getReceiver()),
                coupon.getCreatedAt(),
                coupon.getModifiedAt()
        );
    }
}
