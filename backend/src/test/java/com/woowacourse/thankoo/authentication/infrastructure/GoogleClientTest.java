package com.woowacourse.thankoo.authentication.infrastructure;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("GoogleClient 는")
@ApplicationTest
class GoogleClientTest {

    private static final String INVALID_CLIENT_ID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJodW5pQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20iLCJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhdWQiOiJjbGllbnQifQ.KV8wqyUSHb03rifGsfVyjfA1O2KpJdK69a2EtHHZZl4";
    private static final String INVALID_ISS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJodW5pQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20iLCJpc3MiOiJodHRwczovL2FjY291bnRzLnRoYW5rb28uY29tIiwiYXVkIjoiY2xpZW50LWlkIn0.41Yp6o_IfVVzx5TzJLytcgwW_2uWhYPImwrBBlRK99g";

    @Autowired
    private GoogleClient googleClient;

    @Test
    @DisplayName("토큰이 유효하면 GoogleProfileResponse를 반환한다.")
    void getProfileResponse() {
        GoogleProfileResponse response = googleClient.getProfileResponse(HOHO_TOKEN);

        assertAll(
                () -> assertThat(response.getEmail()).isEqualTo(HOHO_EMAIL),
                () -> assertThat(response.getSocialId()).isEqualTo(HOHO_SOCIAL_ID)
        );
    }

    @ParameterizedTest(name = "토큰 : {0}")
    @ValueSource(strings = {INVALID_CLIENT_ID_TOKEN, INVALID_ISS_TOKEN})
    @DisplayName("client_id 또는 ISS가 올바르지 않은 토큰이라면 예외가 발생한다.")
    void invalidToken(String invalidToken) {
        assertThatThrownBy(() -> googleClient.getProfileResponse(invalidToken))
                .hasMessage("유효하지 않은 토큰입니다.");
    }
}
