package com.woowacourse.thankoo.admin.authentication.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.authentication.infrastructure.AccessTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TokenProvider 는 ")
class TokenProviderTest {

    @DisplayName("토큰을 생성해 반환해야 한다.")
    @Test
    void create() {
        TokenProvider accessTokenProvider = new AccessTokenProvider("acwoFWrRtHoTpxczELrTOhfsQNewXLhviNzE", 1000000);
        String token = accessTokenProvider.create("payload");

        assertThat(token).isNotNull();
    }
}
