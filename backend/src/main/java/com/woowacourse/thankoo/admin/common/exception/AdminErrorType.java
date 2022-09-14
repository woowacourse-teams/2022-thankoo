package com.woowacourse.thankoo.admin.common.exception;

import lombok.Getter;

@Getter
public enum AdminErrorType {

    INVALID_COUPON_STATUS("존재하지 않는 쿠폰 타입입니다."),

    INVALID_DATE_FILTER_CONDITION("날짜 검색 조건이 올바르지 않습니다.");

    private final String message;

    AdminErrorType(final String message) {
        this.message = message;
    }
}
