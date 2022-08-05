package com.woowacourse.thankoo.meeting.application.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MeetingQueryCondition {

    private Long memberId;
    private LocalDateTime time;
    private String status;

    public MeetingQueryCondition(final Long memberId, final LocalDateTime time, final String status) {
        this.memberId = memberId;
        this.time = time;
        this.status = status;
    }
}
