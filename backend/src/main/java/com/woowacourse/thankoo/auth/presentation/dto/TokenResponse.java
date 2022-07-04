package com.woowacourse.thankoo.auth.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenResponse {

    private String accessToken;
    private Long userId;

    public TokenResponse(final String accessToken, final Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
