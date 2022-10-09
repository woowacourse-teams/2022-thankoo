package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.OrganizationAssured.조직_번호;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HUNI_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.LALA_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.HeartAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("HeartAcceptance 는 ")
class HeartAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @DisplayName("상대방에게 마음을 보낸다.")
    @Test
    void sendHeart() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        Organization organization = 기본_조직이_생성됨();
        OrganizationAssured.request()
                .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE));

        HeartAssured.request()
                .마음을_보낸다(organization.getId(), senderToken.getAccessToken(), receiverToken.getMemberId())
                .response()
                .status(HttpStatus.OK.value());
    }

    @DisplayName("상대방과 마음을 주고 받는다.")
    @Test
    void giveAndTakeHeart() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        Organization organization = 기본_조직이_생성됨();
        OrganizationAssured.request()
                .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE));

        HeartAssured.request()
                .마음을_보낸다(organization.getId(), senderToken.getAccessToken(), receiverToken.getMemberId())
                .마음을_보낸다(organization.getId(), receiverToken.getAccessToken(), senderToken.getMemberId())
                .마음을_보낸다(organization.getId(), senderToken.getAccessToken(), receiverToken.getMemberId())
                .response()
                .status(HttpStatus.OK.value());
    }

    @DisplayName("응답 가능한 마음을 조회한다.")
    @Test
    void getReceivedHearts() {
        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse senderToken1 = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        TokenResponse senderToken2 = AuthenticationAssured.request()
                .회원가입_한다(HUNI_TOKEN, HUNI_NAME)
                .token();

        TokenResponse senderToken3 = AuthenticationAssured.request()
                .회원가입_한다(LALA_TOKEN, LALA_NAME)
                .token();

        Organization organization = 기본_조직이_생성됨();
        OrganizationAssured.request()
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(senderToken1, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(senderToken2, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(senderToken3, 조직_번호(ORGANIZATION_THANKOO_CODE));

        HeartAssured.request()
                .마음을_보낸다(organization.getId(), senderToken1.getAccessToken(), receiverToken.getMemberId())
                .마음을_보낸다(organization.getId(), senderToken2.getAccessToken(), receiverToken.getMemberId())
                .마음을_보낸다(organization.getId(), senderToken3.getAccessToken(), receiverToken.getMemberId())
                .마음을_보낸다(organization.getId(), receiverToken.getAccessToken(), senderToken2.getMemberId())
                .응답_가능한_마음을_조회한다(organization.getId(), receiverToken.getAccessToken())
                .response()
                .status(HttpStatus.OK.value())
                .조회_성공(1, 2);
    }

    private Organization 기본_조직이_생성됨() {
        Organization organization = Organization.create(ORGANIZATION_THANKOO,
                length -> ORGANIZATION_THANKOO_CODE,
                30,
                new OrganizationValidator(organizationRepository));
        return organizationRepository.save(organization);
    }
}
