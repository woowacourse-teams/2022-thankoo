package com.woowacourse.thankoo.admin.qrcode.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminLinkResponse {

    private String link;

    public AdminLinkResponse(final String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "AdminLinkResponse{" +
                "link='" + link + '\'' +
                '}';
    }
}
