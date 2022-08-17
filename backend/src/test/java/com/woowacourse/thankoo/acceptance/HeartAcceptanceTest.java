package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.HeartAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("HeartAcceptance 는 ")
public class HeartAcceptanceTest extends AcceptanceTest {

    @DisplayName("상대방에게 마음을 보낸다.")
    @Test
    void sendHeart() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        HeartAssured.request()
                .마음을_보낸다(senderToken.getAccessToken(), receiverToken.getMemberId())
                .response()
                .status(HttpStatus.OK.value());
    }
}
