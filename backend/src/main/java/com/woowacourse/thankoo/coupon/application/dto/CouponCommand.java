package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CouponCommand {

    private final Long organizationId;
    private final Long senderId;
    private final List<Long> receiverIds;
    private final ContentCommand content;

    public CouponCommand(final Long organizationId,
                         final Long senderId,
                         final List<Long> receiverIds,
                         final ContentCommand content) {
        this.organizationId = organizationId;
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.content = content;
    }

    public List<Coupon> toEntities() {
        return receiverIds.stream()
                .map(id -> new Coupon(organizationId, senderId, id, toCouponContent(), CouponStatus.NOT_USED))
                .collect(Collectors.toList());
    }

    public CouponContent toCouponContent() {
        return content.toEntity();
    }
}
