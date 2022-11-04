package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import java.util.Collections;
import java.util.List;

public class MeetingNoticeEvent extends AlarmEvent {

    private final Long organizationId;
    private final List<Long> receiverIds;

    public MeetingNoticeEvent(final Long organizationId,
                              final String alarmType,
                              final List<Long> receiverIds) {
        super(alarmType);
        this.organizationId = organizationId;
        this.receiverIds = receiverIds;
    }

    public static MeetingNoticeEvent from(final Meetings meetings) {
        Long organizationId = meetings.getRepresentativeOrganizationId();

        return new MeetingNoticeEvent(organizationId,
                AlarmSpecification.MEETING_NOTICE,
                meetings.getDistinctMemberIds()
        );
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(), organizationId, receiverIds, Collections.emptyList());
    }

    @Override
    public String toString() {
        return "MeetingNoticeEvent{" +
                "organizationId=" + organizationId +
                ", receiverIds=" + receiverIds +
                '}';
    }
}
