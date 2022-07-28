package com.woowacourse.thankoo.acceptance.support.fixtures;

import com.woowacourse.thankoo.authentication.application.dto.SignUpRequest;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthenticationRequestFixture {

    public static ExtractableResponse<Response> 회원가입_후_로그인_한다(final String code,
                                                              final String idToken,
                                                              final String name) {
        회원가입_한다(idToken, name);
        return 로그인_한다(code);
    }

    public static ExtractableResponse<Response> 회원가입_한다(final String idToken, final String name) {
        return RestAssuredRequestFixture.postWithToken("/api/sign-up", idToken, new SignUpRequest(name));
    }

    public static ExtractableResponse<Response> 로그인_한다(final String code) {
        return RestAssuredRequestFixture.get("/api/sign-in?code=" + code);
    }

    public static TokenResponse 토큰을_반환한다(final ExtractableResponse<Response> response) {
        return response.as(TokenResponse.class);
    }
}
