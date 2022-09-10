package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import java.util.List;

public class ReservationRepliedEvent extends AlarmEvent {

    private final Long receiverId;
    private final Long senderId;
    private final String couponTitle;
    private final ReservationStatus reservationStatus;

    public ReservationRepliedEvent(final String alarmType,
                                   final Long receiverId,
                                   final Long senderId,
                                   final String couponTitle,
                                   final ReservationStatus reservationStatus) {
        super(alarmType);
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.couponTitle = couponTitle;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationRepliedEvent of(final Coupon coupon,
                                             final Long senderId,
                                             final ReservationStatus reservationStatus) {
        return new ReservationRepliedEvent(AlarmSpecification.RESERVATION_REPLIED,
                coupon.getSenderId(),
                senderId,
                coupon.getCouponContent().getTitle(),
                reservationStatus);
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(),
                List.of(receiverId),
                List.of(
                        String.valueOf(senderId),
                        couponTitle,
                        reservationStatus.name()
                ));
    }

    @Override
    public String toString() {
        return "ReservationReplyEvent{" +
                "receiverId=" + receiverId +
                ", senderId=" + senderId +
                ", couponTitle='" + couponTitle + '\'' +
                ", reservationStatus=" + reservationStatus +
                '}';
    }
}
