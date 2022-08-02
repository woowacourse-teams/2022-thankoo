package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;

import com.woowacourse.thankoo.acceptance.dsl.AuthenticationAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("AuthenticationAcceptance 는 ")
class AuthenticationAcceptanceTest extends AcceptanceTest {

    @DisplayName("최초 로그인일 경우 첫 로그인임을 반환한다.")
    @Test
    void signInFirst() {
        AuthenticationAssured
                .request()
                .로그인_한다(CODE_HOHO)
                .response()
                .status(HttpStatus.OK.value())
                .새_회원_이다();
    }

    @DisplayName("기존 회원이 로그인 할 경우 알맞은 토큰을 반환한다.")
    @Test
    void signIn() {
        AuthenticationAssured
                .request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .로그인_한다(CODE_HOHO)
                .response()
                .status(HttpStatus.OK.value())
                .기존_회원_이다();
    }

    @DisplayName("닉네임과 idToken 으로 회원가입을 하고 알맞은 토큰을 반환한다.")
    @Test
    void signUp() {
        AuthenticationAssured
                .request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .response()
                .status(HttpStatus.CREATED.value())
                .기존_회원_이다();
    }
}
