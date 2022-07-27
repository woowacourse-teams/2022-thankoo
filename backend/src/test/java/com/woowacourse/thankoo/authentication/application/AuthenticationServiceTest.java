package com.woowacourse.thankoo.authentication.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("AuthenticationService 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("최초 로그인 시 이메일을 반환한다.")
    @Test
    void signInFirst() {
        TokenResponse tokenResponse = authenticationService.signIn(CODE_HOHO);

        assertAll(
                () -> assertThat(tokenResponse.isJoined()).isFalse(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                () -> assertThat(tokenResponse.getMemberId()).isNull(),
                () -> assertThat(tokenResponse.getEmail()).isEqualTo(HOHO_EMAIL)
        );
    }

    @DisplayName("기존 회원이 로그인 시 토큰을 반환한다.")
    @Test
    void signIn() {
        memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));
        TokenResponse tokenResponse = authenticationService.signIn(CODE_HOHO);

        assertAll(
                () -> assertThat(tokenResponse.isJoined()).isTrue(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                () -> assertThat(tokenResponse.getMemberId()).isNotNull(),
                () -> assertThat(tokenResponse.getEmail()).isEqualTo(HOHO_EMAIL)
        );
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
}
