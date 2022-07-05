package com.woowacourse.thankoo.authentication.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenResponse {

    private String accessToken;
    private Long memberId;

    public TokenResponse(final String accessToken, final Long memberId) {
        this.accessToken = accessToken;
        this.memberId = memberId;
    }
}
