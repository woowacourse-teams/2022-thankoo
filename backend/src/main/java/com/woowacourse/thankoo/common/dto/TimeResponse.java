package com.woowacourse.thankoo.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.meeting.domain.MeetingTime;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TimeResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingTime;
    private String timeZone;

    private TimeResponse(final LocalDateTime meetingTime, final String timeZone) {
        this.meetingTime = meetingTime;
        this.timeZone = timeZone.toLowerCase(Locale.ROOT);
    }

    public static TimeResponse of(final MeetingTime meetingTime) {
        return new TimeResponse(meetingTime.getTime(), meetingTime.getTimeZone());
    }

    public static TimeResponse from(final LocalDateTime meetingTime, final String timeZone) {
        return new TimeResponse(meetingTime, timeZone);
    }
}
