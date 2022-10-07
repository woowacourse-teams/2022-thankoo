package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.OrganizationAssured.조직_번호;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_NO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("OrganizationAcceptance 는 ")
class OrganizationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OrganizationRepository organizationRepository;

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

    private void 기본_조직이_생성됨() {
        Organization organization1 = Organization.create(ORGANIZATION_WOOWACOURSE,
                length -> ORGANIZATION_WOOWACOURSE_CODE,
                30,
                new OrganizationValidator(organizationRepository));

        Organization organization2 = Organization.create(ORGANIZATION_THANKOO,
                length -> ORGANIZATION_THANKOO_CODE,
                30,
                new OrganizationValidator(organizationRepository));
        organizationRepository.save(organization1);
        organizationRepository.save(organization2);
    }
}
