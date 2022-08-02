package com.woowacourse.thankoo.meeting.presentation.dto;

import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.meeting.domain.MeetingCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MeetingResponse {

    private Long id;
    private TimeResponse time;
    private String couponType;
    private String memberName;

    private MeetingResponse(final Long id,
                            final TimeResponse time,
                            final String couponType,
                            final String memberName) {
        this.id = id;
        this.time = time;
        this.couponType = couponType;
        this.memberName = memberName;
    }

    public static MeetingResponse of(final MeetingCoupon meetingCoupon) {
        return new MeetingResponse(meetingCoupon.getId(),
                TimeResponse.of(meetingCoupon.getMeetingTime()),
                meetingCoupon.getCouponType(),
                meetingCoupon.getMemberName());
    }
}
