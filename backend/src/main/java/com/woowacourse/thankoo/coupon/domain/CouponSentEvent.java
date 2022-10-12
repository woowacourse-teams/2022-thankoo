package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import java.util.List;

public class CouponSentEvent extends AlarmEvent {

    private final Long organizationId;
    private final List<Long> receiverIds;
    private final Long senderId;
    private final String couponTitle;
    private final String couponType;

    public CouponSentEvent(final Long organizationId,
                           final String alarmType,
                           final List<Long> receiverIds,
                           final Long senderId,
                           final String couponTitle,
                           final String couponType) {
        super(alarmType);
        this.organizationId = organizationId;
        this.receiverIds = receiverIds;
        this.senderId = senderId;
        this.couponTitle = couponTitle;
        this.couponType = couponType;
    }

    public static CouponSentEvent from(final Coupons coupons) {
        CouponContent couponContent = coupons.getRepresentativeCouponContent();

        return new CouponSentEvent(coupons.getOrganizationId(),
                decideAlarmType(couponContent),
                coupons.getReceiverIds(),
                coupons.getRepresentativeSenderId(),
                couponContent.getTitle(),
                couponContent.getCouponType().getValue()
        );
    }

    private static String decideAlarmType(final CouponContent couponContent) {
        if (couponContent.isCoffeeType()) {
            return AlarmSpecification.COUPON_SENT_COFFEE;
        }
        return AlarmSpecification.COUPON_SENT_MEAL;
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(),
                organizationId,
                receiverIds,
                List.of(String.valueOf(senderId), couponTitle, couponType));
    }

    @Override
    public String toString() {
        return "CouponSentEvent{" +
                "organizationId=" + organizationId +
                ", receiverIds=" + receiverIds +
                ", senderId=" + senderId +
                ", couponTitle='" + couponTitle + '\'' +
                ", couponType='" + couponType + '\'' +
                '}';
    }
}
