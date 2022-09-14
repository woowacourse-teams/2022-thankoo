package com.woowacourse.thankoo.common.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class AlarmSpecification {

    public static final String COUPON_SENT_COFFEE = "coupon_sent_coffee";
    public static final String COUPON_SENT_MEAL = "coupon_sent_meal";
    public static final String RESERVATION_SENT = "reservation_sent";
    public static final String RESERVATION_REPLIED = "reservation_replied";
    public static final String RESERVATION_CANCELED = "reservation_canceled";
    public static final String HEART_SENT = "heart_sent";

    private final String alarmType;
    private final List<Long> targetIds;
    private final List<String> contents;

    public AlarmSpecification(final String alarmType,
                              final List<Long> targetIds,
                              final List<String> contents) {
        this.alarmType = alarmType;
        this.targetIds = targetIds;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "AlarmSpecification{" +
                "alarmType=" + alarmType +
                ", targetIds=" + targetIds +
                ", contents=" + contents +
                '}';
    }
}
