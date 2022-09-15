package com.woowacourse.thankoo.alarm.infrastructure;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AlarmLinkGenerator {

    private final String rootUrl;

    public AlarmLinkGenerator(@Value("${origin}") final String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String createUrl(final String url) {
        if (!url.startsWith("/")) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_LINK);
        }
        return rootUrl + url;
    }
}
