package com.woowacourse.thankoo.common.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class AlarmSpecification {

    public static String COUPON_SENT = "coupon_sent";

    private final String alarmType;
    private final List<Long> targetIds;
    private final String title;
    private final List<String> contents;

    public AlarmSpecification(final String alarmType,
                              final List<Long> targetIds,
                              final String title,
                              final List<String> contents) {
        this.alarmType = alarmType;
        this.targetIds = targetIds;
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "AlarmSpecification{" +
                "alarmType=" + alarmType +
                ", targetIds=" + targetIds +
                ", title='" + title + '\'' +
                ", contents=" + contents +
                '}';
    }
}
