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
    INVALID_COUPON_MESSAGE(3003, "잘못된 쿠폰 내용입니다."),
    INVALID_COUPON_STATUS(3004, "잘못된 쿠폰 상태입니다."),
    NOT_FOUND_COUPON(3005, "존재하지 않는 쿠폰입니다."),

    INVALID_RESERVATION_MEETING_TIME(4001, "유효하지 않은 일정입니다."),
    INVALID_RESERVATION_MEMBER_MISMATCH(4002, "예약을 요청할 수 없는 회원입니다."),
    INVALID_RESERVATION_COUPON_STATUS(4003, "예약 요청이 불가능한 쿠폰입니다."),

    UNHANDLED_EXCEPTION(9999, "예상치 못한 예외입니다.");

    private final int code;
    private final String message;

    ErrorType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
