package com.woowacourse.thankoo.admin.common.exception;

import lombok.Getter;

@Getter
public enum AdminErrorType {

    NOT_FOUND_MEMBER("존재하지 않는 회원입니다."),

    NOT_FOUND_COUPON("존재하지 않는 쿠폰입니다."),
    INVALID_COUPON_STATUS("존재하지 않는 쿠폰 타입입니다."),
    INVALID_COUPON_SEARCH_CONDITION("쿠폰 검색 조건이 올바르지 않습니다."),

    INVALID_MEMBER_SEARCH_CONDITION("회원 검색 조건이 올바르지 않습니다."),

    DUPLICATE_COUPON_SERIAL("시리얼 번호가 중복됩니다."),
    INVALID_COUPON_SERIAL_SIZE("생성할 수 있는 시리얼 번호를 초과했습니다."),

    CANNOT_ENCRYPT_PASSWORD("비밀번호 암호화 중 에러가 발생했습니다.");

    private final String message;

    AdminErrorType(final String message) {
        this.message = message;
    }
}
