package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HUNI;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_LALA;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HUNI_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.LALA_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.MemberAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.application.dto.MemberProfileImageRequest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("MemberAcceptance 는 ")
class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("본인을 제외한 모든 회원을 조회한다.")
    @Test
    void getMembersExcludeMe() {
        TokenResponse tokenResponse = AuthenticationAssured.request()
                .회원가입_한다(LALA_TOKEN, LALA_NAME)
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .회원가입_한다(HUNI_TOKEN, HUNI_NAME)
                .로그인_한다(CODE_HUNI)
                .response()
                .body(TokenResponse.class);

        MemberAssured.request()
                .나를_제외한_모든_회원을_조회한다(tokenResponse)
                .response()
                .status(HttpStatus.OK.value())
                .조회_성공한다(LALA_NAME, SKRR_NAME);
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMember() {
        TokenResponse tokenResponse = AuthenticationAssured.request()
                .회원가입_한다(LALA_TOKEN, LALA_NAME)
                .로그인_한다(CODE_LALA)
                .token();

        MemberAssured.request()
                .내_정보를_조회한다(tokenResponse)
                .response()
                .status(HttpStatus.OK.value())
                .내_정보_이다(MemberResponse.of(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL)));
    }

    @DisplayName("회원 이름을 수정한다.")
    @Test
    void updateName() {
        TokenResponse tokenResponse = AuthenticationAssured.request()
                .회원가입_한다(LALA_TOKEN, LALA_NAME)
                .로그인_한다(CODE_LALA)
                .token();

        MemberAssured.request()
                .내_이름_정보를_수정한다(tokenResponse, new MemberNameRequest(HUNI_NAME))
                .response()
                .status(HttpStatus.NO_CONTENT.value());

        MemberAssured.request()
                .내_정보를_조회한다(tokenResponse)
                .response()
                .내_정보_이다(MemberResponse.of(new Member(HUNI_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL)));
    }

    @DisplayName("회원 프로필 이미지를 수정한다.")
    @Test
    void updateProfileImage() {
        TokenResponse tokenResponse = AuthenticationAssured.request()
                .회원가입_한다(LALA_TOKEN, LALA_NAME)
                .로그인_한다(CODE_LALA)
                .token();

        MemberAssured.request()
                .내_프로필_이미지_정보를_수정한다(tokenResponse, new MemberProfileImageRequest(SKRR_IMAGE_NAME))
                .response()
                .status(HttpStatus.NO_CONTENT.value());

        MemberAssured.request()
                .내_정보를_조회한다(tokenResponse)
                .response()
                .프로필_이미지가_변경되었다(SKRR_IMAGE_URL);
    }

    @DisplayName("사용 가능한 프로필 이미지를 모두 조회한다.")
    @Test
    void getProfileImages() {
        MemberAssured.request()
                .프로필_이미지들을_조회한다()
                .response()
                .status(HttpStatus.OK.value());
    }
}
