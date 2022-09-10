package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import java.util.List;
import lombok.Getter;

@Getter
public class CouponSentAlarmEvent extends AlarmEvent {

    private final List<Long> receiverIds;
    private final Long senderId;
    private final String couponTitle;
    private final String couponType;

    public CouponSentAlarmEvent(final String alarmType,
                                final List<Long> receiverIds,
                                final Long senderId,
                                final String couponTitle,
                                final String couponType) {
        super(alarmType);
        this.receiverIds = receiverIds;
        this.senderId = senderId;
        this.couponTitle = couponTitle;
        this.couponType = couponType;
    }

    public static CouponSentAlarmEvent from(final Coupons coupons) {
        CouponContent couponContent = coupons.getRepresentativeCouponContent();

        return new CouponSentAlarmEvent(decideAlarmType(couponContent),
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
                receiverIds,
                List.of(String.valueOf(senderId), couponTitle, couponType));
    }

    @Override
    public String toString() {
        return "CouponSentAlarmEvent{" +
                "receiverIds=" + receiverIds +
                ", senderId=" + senderId +
                ", couponTitle='" + couponTitle + '\'' +
                ", couponType='" + couponType + '\'' +
                '}';
    }
}
