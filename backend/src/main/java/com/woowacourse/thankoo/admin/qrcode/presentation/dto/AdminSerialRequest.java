package com.woowacourse.thankoo.admin.qrcode.presentation.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminSerialRequest {

    private List<String> serials;

    public AdminSerialRequest(final List<String> serials) {
        this.serials = serials;
    }
}
