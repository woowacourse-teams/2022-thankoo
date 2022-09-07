package com.woowacourse.thankoo.alarm.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class Alarm {

    private final AlarmType alarmType;
    private final List<Long> targetIds;
    private final String title;
    private final List<String> contents;

    public Alarm(final AlarmType alarmType,
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
        return "Alarm{" +
                "alarmType=" + alarmType +
                ", targetIds=" + targetIds +
                ", title='" + title + '\'' +
                ", contents=" + contents +
                '}';
    }
}
