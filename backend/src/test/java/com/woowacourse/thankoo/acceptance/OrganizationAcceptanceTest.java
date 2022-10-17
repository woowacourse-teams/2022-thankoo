package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.OrganizationAssured.조직_번호;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HUNI_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_NO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("OrganizationAcceptance 는 ")
class OrganizationAcceptanceTest extends AcceptanceTest {

    @DisplayName("내 조직을 조회할 떄 ")
    @Nested
    class MyOrganizationTest {

        @DisplayName("조직이 없을 경우 예외가 발생한다.")
        @Test
        void organizationNotExist() {
            TokenResponse userToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationAssured.request()
                    .내_조직을_조회한다(userToken)
                    .response()
                    .status(HttpStatus.OK.value())
                    .내_조직이_없다();
        }

        @DisplayName("조직이 있을 경우 조회한다.")
        @Test
        void getMyOrganizations() {
            TokenResponse userToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationAssured.request()
                    .조직에_참여한다(userToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(userToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                    .내_조직을_조회한다(userToken)
                    .response()
                    .status(HttpStatus.OK.value())
                    .조직을_조회한다(ORGANIZATION_WOOWACOURSE_CODE, ORGANIZATION_THANKOO_CODE);
        }
    }

    @DisplayName("코드로 단건 조직을 조회할 때 ")
    @Nested
    class SingleOrganizationTest {

        @DisplayName("있으면 조회한다.")
        @Test
        void getOrganization() {
            TokenResponse userToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();

            OrganizationAssured.request()
                    .단건_조직을_조회한다(userToken, ORGANIZATION_WOOWACOURSE_CODE)
                    .response()
                    .status(HttpStatus.OK.value())
                    .코드명_조직이_조회됨(ORGANIZATION_WOOWACOURSE);
        }

        @DisplayName("없으면 예외가 발생한다.")
        @Test
        void noOrganization() {
            TokenResponse userToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            OrganizationAssured.request()
                    .단건_조직을_조회한다(userToken, ORGANIZATION_WOOWACOURSE_CODE)
                    .response()
                    .status(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("조직에 참여할 때 ")
    @Nested
    class JoinTest {

        @DisplayName("조직이 없으면 실패한다.")
        @Test
        void noOrganizationFailed() {
            TokenResponse userToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            OrganizationAssured.request()
                    .조직에_참여한다(userToken, 조직_번호(ORGANIZATION_NO_CODE))
                    .response()
                    .status(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("성공한다.")
        @Test
        void success() {
            TokenResponse userToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationAssured.request()
                    .조직에_참여한다(userToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .response()
                    .status(HttpStatus.OK.value());
        }
    }

    @DisplayName("조직에 접근한다.")
    @Test
    void access() {
        TokenResponse userToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .response()
                .body(TokenResponse.class);

        기본_조직이_생성됨();
        OrganizationResponse organizationResponse = OrganizationAssured.request()
                .조직에_참여한다(userToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .조직에_참여한다(userToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .내_조직을_조회한다(userToken)
                .response()
                .bodies(OrganizationResponse.class).get(0);

        OrganizationAssured.request()
                .조직에_접근한다(userToken, organizationResponse.getOrganizationId())
                .response()
                .status(HttpStatus.OK.value());

        OrganizationAssured.request()
                .내_조직을_조회한다(userToken)
                .response()
                .조직상태가_변경됨(organizationResponse.getOrganizationId(), true);
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

        OrganizationAssured.request()
                .나를_제외한_조직의_모든_회원을_조회한다(userToken, organizationResponse.getOrganizationId())
                .response()
                .status(HttpStatus.OK.value())
                .나를_제외하고_모두_조회됨(2);
    }
}
