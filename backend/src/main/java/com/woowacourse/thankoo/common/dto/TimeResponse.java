package com.woowacourse.thankoo.common.dto;

import com.woowacourse.thankoo.meeting.domain.MeetingTime;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TimeResponse {

    private LocalDateTime meetingTime;
    private String timeZone;

    public TimeResponse(final LocalDateTime meetingTime, final String timeZone) {
        this.meetingTime = meetingTime;
        this.timeZone = timeZone.toLowerCase(Locale.ROOT);
    }

    public static TimeResponse of(final MeetingTime meetingTime) {
        return new TimeResponse(meetingTime.getTime(), meetingTime.getTimeZone());
    }
}
