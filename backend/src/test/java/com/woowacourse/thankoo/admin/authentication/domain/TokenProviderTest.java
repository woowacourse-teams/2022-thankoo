package com.woowacourse.thankoo.admin.authentication.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("TokenProvider 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TokenProviderTest {

    @Autowired
    private TokenProvider accessTokenProvider;

    @DisplayName("토큰을 생성해 반환해야 한다.")
    @Test
    void create() {
        String token = accessTokenProvider.create("payload");

        assertThat(token).isNotNull();
    }
}
