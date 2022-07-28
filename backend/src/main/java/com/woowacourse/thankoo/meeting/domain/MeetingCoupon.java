package com.woowacourse.thankoo.meeting.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MeetingCoupon {

    private final Long id;
    private final LocalDateTime meetingTime;
    private final String couponType;
    private final String memberName;


    public MeetingCoupon(final Long id,
                         final LocalDateTime meetingTime,
                         final String couponType,
                         final String memberName) {
        this.id = id;
        this.meetingTime = meetingTime;
        this.couponType = couponType;
        this.memberName = memberName;
    }
}
