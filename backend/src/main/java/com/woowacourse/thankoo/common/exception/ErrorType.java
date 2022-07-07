package com.woowacourse.thankoo.common.exception;

import lombok.Getter;

@Getter
public enum ErrorType {

    NOT_AUTHENTICATED(1001, "인증되지 않았습니다."),
    ALREADY_AUTHENTICATED(1002, "이미 인증정보가 존재합니다."),
    INVALID_TOKEN(1003, "유효하지 않은 토큰입니다."),

    NOT_FOUND_MEMBER(2001, "존재하지 않는 회원입니다."),

    INVALID_COUPON_TYPE(3001, "존재하지 않는 쿠폰 타입입니다."),
    INVALID_COUPON_TITLE(3002, "잘못된 쿠폰 제목입니다."),
    INVALID_COUPON_MESSAGE(3003, "잘못된 쿠폰 내용입니다.")
    ;

    private final int code;
    private final String message;

    ErrorType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
