package com.woowacourse.thankoo.serial.domain;

public enum CouponSerialStatus {

    NOT_USED, USED;

    public boolean isUsed() {
        return this == USED;
    }
}
