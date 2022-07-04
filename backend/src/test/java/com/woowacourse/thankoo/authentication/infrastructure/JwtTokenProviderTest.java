package com.woowacourse.thankoo.authentication.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.authentication.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JwtTokenProvider 는 ")
class JwtTokenProviderTest {

    private static final String SECRET_KEY = "dasdc338hfhghsn21sdf1jvnu4ascasv21908fyhas2a";
    private static final int VALIDITY_IN_MILLISECONDS = 1000000;

    @DisplayName("토큰을 생성한다.")
    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);
        String token = jwtTokenProvider.createToken("1");
        assertThat(token).isNotNull();
    }

    @DisplayName("페이로드를 가져온다.")
    @Test
    void getPayload() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);
        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo("1");
    }

    @DisplayName("기간이 만료되면 에러가 발생한다.")
    @Test
    void getPayloadExpiredException() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, 0);
        String token = jwtTokenProvider.createToken("1");

        assertThatThrownBy(() -> jwtTokenProvider.getPayload(token))
                .isInstanceOf(InvalidTokenException.class);
    }

}
