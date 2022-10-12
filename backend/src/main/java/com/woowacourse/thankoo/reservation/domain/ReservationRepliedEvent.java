package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import java.util.List;

public class ReservationRepliedEvent extends AlarmEvent {

    private final Long receiverId;
    private final Long organizationId;
    private final Long senderId;
    private final String couponTitle;
    private final ReservationStatus reservationStatus;

    public ReservationRepliedEvent(final String alarmType,
                                   final Long organizationId,
                                   final Long receiverId,
                                   final Long senderId,
                                   final String couponTitle,
                                   final ReservationStatus reservationStatus) {
        super(alarmType);
        this.organizationId = organizationId;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.couponTitle = couponTitle;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationRepliedEvent of(final Long receiverId,
                                             final Coupon coupon,
                                             final ReservationStatus reservationStatus) {
        return new ReservationRepliedEvent(AlarmSpecification.RESERVATION_REPLIED,
                coupon.getOrganizationId(),
                receiverId,
                coupon.getSenderId(),
                coupon.getCouponContent().getTitle(),
                reservationStatus);
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(),
                organizationId,
                List.of(receiverId),
                List.of(
                        String.valueOf(senderId),
                        couponTitle,
                        reservationStatus.name()
                ));
    }

    @Override
    public String toString() {
        return "ReservationRepliedEvent{" +
                "receiverId=" + receiverId +
                ", organizationId=" + organizationId +
                ", senderId=" + senderId +
                ", couponTitle='" + couponTitle + '\'' +
                ", reservationStatus=" + reservationStatus +
                '}';
    }
}
