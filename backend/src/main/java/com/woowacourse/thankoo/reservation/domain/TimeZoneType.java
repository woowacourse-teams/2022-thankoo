package com.woowacourse.thankoo.reservation.domain;

import lombok.Getter;

@Getter
public enum TimeZoneType {

    ASIA_SEOUL("Asia/Seoul");

    private final String id;

    TimeZoneType(String id) {
        this.id = id;
    }
}
