package com.woowacourse.thankoo.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AuthorizationExtractor 는 ")
@ExtendWith(MockitoExtension.class)
class AuthorizationExtractorTest {

    private static final String AUTHORIZATION_HEADER_TYPE = "Authorization";

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @DisplayName("Authorization 헤더에 값이 존재하지 않는다.")
    @Test
    void extractNothingEmpty() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn(null);

        assertThat(AuthorizationExtractor.extract(request)).isEmpty();
    }

    @DisplayName("헤더에 값이 존재하지만 type 이 다르다.")
    @Test
    void extractNotMatchTypeEmpty() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn("Error jwt.token.here");

        assertThat(AuthorizationExtractor.extract(request)).isEmpty();
    }

    @DisplayName("토큰값이 존재하지 않는다.")
    @Test
    void extractEmptyTokenEmpty() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn("Bearer");

        assertThat(AuthorizationExtractor.extract(request)).isEmpty();
    }

    @DisplayName("정상적으로 extract 한 토큰값을 반환한다.")
    @Test
    void extract() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn("Bearer jwt.token.here");

        assertThat(AuthorizationExtractor.extract(request)).isEqualTo(Optional.of("jwt.token.here"));
    }
}
