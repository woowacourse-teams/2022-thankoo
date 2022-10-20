package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.OrganizationAssured.조직_번호;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_LALA;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HUNI_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.LALA_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.MemberAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.application.dto.MemberProfileImageRequest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("MemberAcceptance 는 ")
class MemberAcceptanceTest extends AcceptanceTest {

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
                .내_프로필_이미지_정보를_수정한다(tokenResponse, new MemberProfileImageRequest(SKRR_IMAGE_URL))
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

    @DisplayName("나를 제외한 조직의 모든 회원을 조회한다.")
    @Test
    void getMyOrganizations() {
        TokenResponse userToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .response()
                .body(TokenResponse.class);

        TokenResponse hohoToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        TokenResponse huniToken = AuthenticationAssured.request()
                .회원가입_한다(HUNI_TOKEN, HUNI_NAME)
                .token();

        기본_조직이_생성됨();
        OrganizationResponse organizationResponse = OrganizationAssured.request()
                .조직에_참여한다(userToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .조직에_참여한다(hohoToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .조직에_참여한다(huniToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .내_조직을_조회한다(userToken)
                .response()
                .bodies(OrganizationResponse.class).get(0);

        MemberAssured.request()
                .나를_제외한_조직의_모든_회원을_조회한다(userToken, organizationResponse.getOrganizationId())
                .response()
                .status(HttpStatus.OK.value())
                .나를_제외하고_모두_조회됨(2);
    }
}
