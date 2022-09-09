package com.woowacourse.thankoo.common.domain;

import com.woowacourse.thankoo.alarm.domain.AlarmType;
import java.util.List;
import lombok.Getter;

@Getter
public class AlarmSpecification {

    private final AlarmType alarmType;
    private final List<Long> targetIds;
    private final String title;
    private final List<String> contents;

    public AlarmSpecification(final AlarmType alarmType,
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
