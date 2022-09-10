package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationSentAlarmEvent extends AlarmEvent {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    private final Long receiverId;
    private final Long senderId;
    private final String couponTitle;
    private final LocalDateTime reservationTime;

    public ReservationSentAlarmEvent(final String alarmType,
                                     final Long receiverId,
                                     final Long senderId,
                                     final String couponTitle,
                                     final LocalDateTime reservationTime) {
        super(alarmType);
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.reservationTime = reservationTime;
        this.couponTitle = couponTitle;
    }

    public static ReservationSentAlarmEvent from(final Reservation reservation) {
        Coupon reservedCoupon = reservation.getCoupon();
        return new ReservationSentAlarmEvent(AlarmSpecification.RESERVATION_SENT,
                reservedCoupon.getSenderId(),
                reservation.getMemberId(),
                reservedCoupon.getCouponContent().getTitle(),
                reservation.getTimeUnit().getTime());
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(),
                List.of(receiverId),
                List.of(String.valueOf(senderId),
                        couponTitle,
                        reservationTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))));
    }

    @Override
    public String toString() {
        return "ReservationSentAlarmEvent{" +
                "receiverId=" + receiverId +
                ", senderId=" + senderId +
                ", couponTitle='" + couponTitle + '\'' +
                ", reservationTime=" + reservationTime +
                '}';
    }
}
