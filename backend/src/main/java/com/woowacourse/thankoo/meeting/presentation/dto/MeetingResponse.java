package com.woowacourse.thankoo.meeting.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.meeting.domain.MeetingCoupon;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MeetingResponse {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingTime;
    private String couponType;
    private String memberName;

    public MeetingResponse(final Long id,
                           final LocalDateTime meetingTime,
                           final String couponType,
                           final String memberName) {
        this.id = id;
        this.meetingTime = meetingTime;
        this.couponType = couponType.toLowerCase(Locale.ROOT);
        this.memberName = memberName;
    }

    public static MeetingResponse of(final MeetingCoupon meetingCoupon) {
        return new MeetingResponse(meetingCoupon.getId(),
                meetingCoupon.getMeetingTime().getTime(),
                meetingCoupon.getCouponType(),
                meetingCoupon.getMemberName());
    }
}
