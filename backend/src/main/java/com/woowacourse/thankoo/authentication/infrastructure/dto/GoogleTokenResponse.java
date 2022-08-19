package com.woowacourse.thankoo.authentication.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GoogleTokenResponse {

    @JsonProperty("id_token")
    private String idToken;

    public GoogleTokenResponse(final String idToken) {
        this.idToken = idToken;
    }
}
