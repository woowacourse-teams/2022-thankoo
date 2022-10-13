package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponRequest {

    private List<Long> receiverIds;
    private ContentRequest content;

    public CouponRequest(final List<Long> receiverIds, final ContentRequest content) {
        this.receiverIds = receiverIds;
        this.content = content;
    }

    @Deprecated
    public List<Coupon> toEntities(final Long senderId) {
        return receiverIds.stream()
                .map(id -> new Coupon(1L, senderId, id, content.toEntity(), CouponStatus.NOT_USED))
                .collect(Collectors.toList());
    }

    public CouponCommand toCouponCommand(final Long organizationId, final Long senderId) {
        return new CouponCommand(organizationId, senderId, receiverIds, content.toCouponCommand());
    }

    @Override
    public String toString() {
        return "CouponRequest{" +
                "receiverIds=" + receiverIds +
                ", content=" + content +
                '}';
    }
}
