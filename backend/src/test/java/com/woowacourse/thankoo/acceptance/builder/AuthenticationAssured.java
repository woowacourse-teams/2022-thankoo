package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.get;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.authentication.application.dto.SignUpRequest;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthenticationAssured {

    private final ExtractableResponse<Response> response;

    public AuthenticationAssured(final ExtractableResponse<Response> response) {
        this.response = response;
    }

    public static AuthenticationRequestBuilder request() {
        return new AuthenticationRequestBuilder();
    }

    public static class AuthenticationRequestBuilder extends RequestBuilder {

        public AuthenticationRequestBuilder 회원가입_한다(final String idToken, final String name) {
            response = postWithToken("/api/sign-up", idToken, new SignUpRequest(name));
            return this;
        }

        public AuthenticationRequestBuilder 로그인_한다(final String code) {
            response = get("/api/sign-in?code=" + code);
            return this;
        }

        public AuthenticationResponseBuilder response() {
            return new AuthenticationResponseBuilder(response);
        }
    }

    public static class AuthenticationResponseBuilder extends ResponseBuilder {

        public AuthenticationResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public AuthenticationResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }

        public void 새_회원_이다() {
            TokenResponse tokenResponse = response.as(TokenResponse.class);
            assertAll(
                    () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                    () -> assertThat(tokenResponse.isJoined()).isFalse()
            );
        }

        public void 기존_회원_이다() {
            TokenResponse tokenResponse = response.as(TokenResponse.class);
            assertAll(
                    () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                    () -> assertThat(tokenResponse.isJoined()).isTrue()
            );
        }
    }
}
