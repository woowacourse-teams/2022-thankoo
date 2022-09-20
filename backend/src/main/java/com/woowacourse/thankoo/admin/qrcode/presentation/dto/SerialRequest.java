package com.woowacourse.thankoo.admin.qrcode.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SerialRequest {

    private String serial;

    public SerialRequest(final String serial) {
        this.serial = serial;
    }
}
