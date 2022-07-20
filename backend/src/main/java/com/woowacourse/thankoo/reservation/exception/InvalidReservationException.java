package com.woowacourse.thankoo.reservation.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidReservationException extends BadRequestException {

    public InvalidReservationException(final ErrorType errorType) {
        super(errorType);
    }
}
