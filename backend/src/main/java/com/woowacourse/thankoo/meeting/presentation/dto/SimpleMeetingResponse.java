package com.woowacourse.thankoo.meeting.presentation.dto;

import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.meeting.domain.MeetingCoupon;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SimpleMeetingResponse {

    private Long meetingId;
    private TimeResponse time;
    private String couponType;
    private String memberName;

    private SimpleMeetingResponse(final Long meetingId,
                                  final TimeResponse time,
                                  final String couponType,
                                  final String memberName) {
        this.meetingId = meetingId;
        this.time = time;
        this.couponType = couponType.toLowerCase(Locale.ROOT);
        this.memberName = memberName;
    }

    public static SimpleMeetingResponse of(final MeetingCoupon meetingCoupon) {
        return new SimpleMeetingResponse(meetingCoupon.getId(),
                TimeResponse.of(meetingCoupon.getTimeUnit()),
                meetingCoupon.getCouponType(),
                meetingCoupon.getMemberName());
    }

    @Override
    public String toString() {
        return "SimpleMeetingResponse{" +
                "meetingId=" + meetingId +
                ", time=" + time +
                ", couponType='" + couponType + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
