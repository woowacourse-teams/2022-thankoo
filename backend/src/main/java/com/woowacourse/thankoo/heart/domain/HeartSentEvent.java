package com.woowacourse.thankoo.heart.domain;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.common.dto.AlarmEvent;
import java.util.List;

public class HeartSentEvent extends AlarmEvent {

    private final Long organizationId;
    private final Long receiverId;
    private final Long senderId;
    private final int count;

    private HeartSentEvent(final String alarmType,
                           final Long organizationId,
                           final Long receiverId,
                           final Long senderId,
                           final int count) {
        super(alarmType);
        this.organizationId = organizationId;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.count = count;
    }

    public static HeartSentEvent from(final Heart heart) {
        return new HeartSentEvent(AlarmSpecification.HEART_SENT,
                heart.getOrganizationId(),
                heart.getReceiverId(),
                heart.getSenderId(),
                heart.getCount());
    }

    @Override
    public AlarmSpecification toAlarmSpecification() {
        return new AlarmSpecification(getAlarmType(),
                organizationId,
                List.of(receiverId),
                List.of(
                        String.valueOf(senderId),
                        String.valueOf(count)
                ));
    }

    @Override
    public String toString() {
        return "HeartSentEvent{" +
                "organizationId=" + organizationId +
                ", receiverId=" + receiverId +
                ", senderId=" + senderId +
                ", count=" + count +
                '}';
    }
}
