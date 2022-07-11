package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponHistory;
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

    public List<CouponHistory> toEntities(final Long senderId) {
        CouponContent couponContent = content.toEntity();
        return receiverIds.stream()
                .map(id -> new CouponHistory(senderId, id, couponContent))
                .collect(Collectors.toList());
    }
}
