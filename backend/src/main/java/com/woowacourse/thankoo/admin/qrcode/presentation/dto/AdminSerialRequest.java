package com.woowacourse.thankoo.admin.qrcode.presentation.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminSerialRequest {

    private List<String> serials;
    private Long organizationId;

    public AdminSerialRequest(final List<String> serials, final Long organizationId) {
        this.serials = serials;
        this.organizationId = organizationId;
    }
}
