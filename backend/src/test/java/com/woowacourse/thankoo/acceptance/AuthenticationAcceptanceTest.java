package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.회원가입_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.회원가입_후_로그인_한다;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("AuthenticationAcceptance 는 ")
class AuthenticationAcceptanceTest extends AcceptanceTest {

    @DisplayName("최초 로그인일 경우 첫 로그인임을 반환한다.")
    @Test
    void signInFirst() {
        ExtractableResponse<Response> response = 로그인_한다(CODE_HOHO);
        TokenResponse tokenResponse = 토큰을_반환한다(response);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                () -> assertThat(tokenResponse.isJoined()).isFalse()
        );
    }

    @DisplayName("기존 회원이 로그인 할 경우 알맞은 토큰을 반환한다.")
    @Test
    void signIn() {
        ExtractableResponse<Response> response = 회원가입_후_로그인_한다(CODE_HOHO, HOHO_TOKEN, HOHO_NAME);
        TokenResponse tokenResponse = 토큰을_반환한다(response);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                () -> assertThat(tokenResponse.isJoined()).isTrue()
        );
    }

    @DisplayName("닉네임과 idToken 으로 회원가입을 하고 알맞은 토큰을 반환한다.")
    @Test
    void signUp() {
        ExtractableResponse<Response> response = 회원가입_한다(HOHO_TOKEN, HOHO_NAME);
        TokenResponse tokenResponse = 토큰을_반환한다(response);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                () -> assertThat(tokenResponse.isJoined()).isTrue()
        );
    }
}
