package com.woowacourse.thankoo.common.exception.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorResponse {

    private int errorCode;
    private String message;

    public ErrorResponse(final int errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
