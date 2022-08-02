package com.woowacourse.thankoo.acceptance.dsl;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.get;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.application.dto.SignUpRequest;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthenticationAssured {

    private final ExtractableResponse<Response> response;

    public AuthenticationAssured(final ExtractableResponse<Response> response) {
        this.response = response;
    }

    public static AuthenticationRequestDsl request() {
        return new AuthenticationRequestDsl();
    }

    public static class AuthenticationRequestDsl {

        private ExtractableResponse<Response> response;

        public AuthenticationRequestDsl 회원가입_한다(final String idToken, final String name) {
            response = postWithToken("/api/sign-up", idToken, new SignUpRequest(name));
            return this;
        }

        public AuthenticationRequestDsl 로그인_한다(final String code) {
            response = get("/api/sign-in?code=" + code);
            return this;
        }

        public void done() {
            if (response == null) {
                throw new RuntimeException("request cannot be done before returning ExtractableResponse");
            }
        }

        public AuthenticationResponseDsl response() {
            return new AuthenticationResponseDsl(response);
        }
    }

    public static class AuthenticationResponseDsl {

        private final ExtractableResponse<Response> response;

        public AuthenticationResponseDsl(final ExtractableResponse<Response> response) {
            this.response = response;
        }

        public AuthenticationResponseDsl status(final int code) {
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
