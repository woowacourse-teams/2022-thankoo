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

    // TODO : Organization Id 를 parameter로 받으면 추가한다.
    public List<Coupon> toEntities(final Long senderId) {
        return receiverIds.stream()
                .map(id -> new Coupon(1L, senderId, id, toCouponContent(), CouponStatus.NOT_USED))
                .collect(Collectors.toList());
    }

    public CouponContent toCouponContent() {
        return content.toEntity();
    }

    @Override
    public String toString() {
        return "CouponRequest{" +
                "receiverIds=" + receiverIds +
                ", content=" + content +
                '}';
    }
}
