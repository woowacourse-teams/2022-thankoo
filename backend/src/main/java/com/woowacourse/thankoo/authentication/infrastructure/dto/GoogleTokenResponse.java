package com.woowacourse.thankoo.authentication.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GoogleTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    public GoogleTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
