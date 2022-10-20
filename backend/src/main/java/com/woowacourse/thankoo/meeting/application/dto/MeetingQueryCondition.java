package com.woowacourse.thankoo.meeting.application.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MeetingQueryCondition {

    private Long memberId;
    private Long organizationId;
    private LocalDateTime time;
    private String status;

    public MeetingQueryCondition(final Long memberId, final Long organizationId, final LocalDateTime time,
                                 final String status) {
        this.memberId = memberId;
        this.organizationId = organizationId;
        this.time = time;
        this.status = status;
    }

    @Override
    public String toString() {
        return "MeetingQueryCondition{" +
                "memberId=" + memberId +
                ", organizationId=" + organizationId +
                ", time=" + time +
                ", status='" + status + '\'' +
                '}';
    }
}
