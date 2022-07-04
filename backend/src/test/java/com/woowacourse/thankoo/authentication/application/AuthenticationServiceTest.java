package com.woowacourse.thankoo.authentication.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("AuthenticationService 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("SignIn 시 토큰을 반환한다.")
    @Test
    void signIn() {
        TokenResponse tokenResponse = authenticationService.signIn("huni");

        assertAll(
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull(),
                () -> assertThat(tokenResponse.getMemberId()).isNotNull()
        );
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
}
