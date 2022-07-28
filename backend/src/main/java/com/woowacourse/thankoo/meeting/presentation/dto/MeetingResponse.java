package com.woowacourse.thankoo.meeting.presentation.dto;

import com.woowacourse.thankoo.meeting.domain.MeetingCoupon;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MeetingResponse {

    private Long id;
    private LocalDateTime meetingTime;
    private String couponType;
    private String memberName;

    public MeetingResponse(final Long id,
                           final LocalDateTime meetingTime,
                           final String couponType,
                           final String memberName) {
        this.id = id;
        this.meetingTime = meetingTime;
        this.couponType = couponType;
        this.memberName = memberName;
    }

    public static MeetingResponse of(final MeetingCoupon meetingCoupon) {
        return new MeetingResponse(meetingCoupon.getId(),
                meetingCoupon.getMeetingTime(),
                meetingCoupon.getCouponType(),
                meetingCoupon.getMemberName());
    }
}
