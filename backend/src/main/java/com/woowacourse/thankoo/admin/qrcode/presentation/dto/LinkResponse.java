package com.woowacourse.thankoo.admin.qrcode.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LinkResponse {

    private String link;

    public LinkResponse(final String link) {
        this.link = link;
    }
}
