package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.alarm.domain.AlarmType;
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

    public CouponSentAlarmEvent(final List<Long> receiverIds,
                                final Long senderId,
                                final String couponTitle,
                                final String couponType) {
        super(AlarmType.COUPON_SENT);
        this.receiverIds = receiverIds;
        this.senderId = senderId;
        this.couponTitle = couponTitle;
        this.couponType = couponType;
    }

    public static CouponSentAlarmEvent from(final Coupons coupons) {
        CouponContent couponContent = coupons.getRepresentativeCouponContent();
        return new CouponSentAlarmEvent(coupons.getReceiverIds(),
                coupons.getRepresentativeSenderId(),
                couponContent.getTitle(),
                couponContent.getCouponType().getValue()
        );
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(),
                receiverIds,
                couponType,
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
