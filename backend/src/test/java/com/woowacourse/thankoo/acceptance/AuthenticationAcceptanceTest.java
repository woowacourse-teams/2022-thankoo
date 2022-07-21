package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("AuthenticationAcceptance 는 ")
class AuthenticationAcceptanceTest extends AcceptanceTest {

    @DisplayName("유저가 로그인을 진행하면 알맞은 토큰을 반환한다.")
    @Test
    void signIn() {
        ExtractableResponse<Response> response = 로그인_한다(CODE_HOHO);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(토큰을_반환한다(response).getAccessToken()).isNotNull()
        );
    }
}
