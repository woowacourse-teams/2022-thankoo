package com.woowacourse.thankoo.acceptance.support.fixtures;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthenticationRequestFixture {

    public static ExtractableResponse<Response> 로그인_한다(final String name) {
        return RestAssuredRequestFixture.get("/api/sign-in?code=" + name);
    }

    public static TokenResponse 토큰을_반환한다(final ExtractableResponse<Response> response) {
        return response.as(TokenResponse.class);
    }
}
