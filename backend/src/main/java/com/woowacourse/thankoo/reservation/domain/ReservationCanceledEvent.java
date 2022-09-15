package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import java.util.List;

public class ReservationCanceledEvent extends AlarmEvent {

    private final Long receiverId;
    private final Long senderId;
    private final String couponTitle;

    public ReservationCanceledEvent(final String alarmType,
                                    final Long receiverId,
                                    final Long senderId,
                                    final String couponTitle) {
        super(alarmType);
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.couponTitle = couponTitle;
    }

    public static ReservationCanceledEvent of(final Coupon coupon, final Long senderId) {
        return new ReservationCanceledEvent(AlarmSpecification.RESERVATION_CANCELED,
                coupon.getSenderId(),
                senderId,
                coupon.getCouponContent().getTitle());
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(),
                List.of(receiverId),
                List.of(
                        String.valueOf(senderId),
                        couponTitle
                ));
    }

    @Override
    public String toString() {
        return "ReservationCanceledEvent{" +
                "receiverId=" + receiverId +
                ", senderId=" + senderId +
                ", couponTitle='" + couponTitle + '\'' +
                '}';
    }
}
