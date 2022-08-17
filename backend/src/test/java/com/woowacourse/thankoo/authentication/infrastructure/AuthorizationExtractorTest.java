package com.woowacourse.thankoo.authentication.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AuthorizationExtractor 는 ")
@ExtendWith(MockitoExtension.class)
class AuthorizationExtractorTest {

    private static final String AUTHORIZATION_HEADER_TYPE = "Authorization";

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @DisplayName("Authorization 헤더 값이 올바르지 않을 경우 Empty를 반환한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Error jwt.token.here", "Bearer"})
    void extractNothingEmpty(String token) {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn(token);

        assertThat(AuthorizationExtractor.extract(request)).isEmpty();
    }

    @DisplayName("정상적으로 extract 한 토큰값을 반환한다.")
    @Test
    void extract() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn("Bearer jwt.token.here");

        assertThat(AuthorizationExtractor.extract(request)).isEqualTo(Optional.of("jwt.token.here"));
    }
}
