package com.woowacourse.thankoo.common.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

    private final int code;

    public ForbiddenException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
