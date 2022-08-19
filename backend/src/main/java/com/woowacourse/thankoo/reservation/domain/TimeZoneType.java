package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum TimeZoneType {

    ASIA_SEOUL("Asia/Seoul");

    private final String id;

    TimeZoneType(final String id) {
        this.id = id;
    }

    public static TimeZoneType of(final String id) {
        return Arrays.stream(values())
                .filter(timeZoneType -> id.equals(timeZoneType.id))
                .findFirst()
                .orElseThrow(() -> new InvalidMeetingException(ErrorType.INVALID_MEETING_TIME_ZONE));
    }
}
