package com.woowacourse.thankoo.alarm.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import java.util.List;
import lombok.Getter;

@Getter
public class Alarm {

    private final AlarmType alarmType;
    private final Long organizationId;
    private final List<Long> targetIds;
    private final List<String> contents;
    private final AlarmStatus alarmStatus;

    public Alarm(final AlarmType alarmType, final Long organizationId, final List<Long> targetIds,
                 final List<String> contents,
                 final AlarmStatus alarmStatus) {
        this.alarmType = alarmType;
        this.organizationId = organizationId;
        this.targetIds = targetIds;
        this.contents = contents;
        this.alarmStatus = alarmStatus;
    }

    public static Alarm create(final AlarmSpecification alarmSpecification) {
        return new Alarm(AlarmType.from(alarmSpecification.getAlarmType()),
                alarmSpecification.getOrganizationId(),
                alarmSpecification.getTargetIds(),
                alarmSpecification.getContents(),
                AlarmStatus.CREATED);
    }

    public boolean hasContentsSize(final int size) {
        return contents.size() == size;
    }

    public String getContentAt(final int index) {
        return contents.get(index);
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmType=" + alarmType +
                ", organizationId=" + organizationId +
                ", targetIds=" + targetIds +
                ", contents=" + contents +
                ", alarmStatus=" + alarmStatus +
                '}';
    }
}
