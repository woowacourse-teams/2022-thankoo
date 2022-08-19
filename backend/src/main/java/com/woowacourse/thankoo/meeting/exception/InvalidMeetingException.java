package com.woowacourse.thankoo.meeting.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidMeetingException extends BadRequestException {

    public InvalidMeetingException(final ErrorType errorType) {
        super(errorType);
    }
}
