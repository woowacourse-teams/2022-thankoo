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
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.HeartAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("HeartAcceptance 는 ")
class HeartAcceptanceTest extends AcceptanceTest {

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

        기본_조직이_생성됨();
        OrganizationResponse organizationResponse = OrganizationAssured.request()
                .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .내_조직을_조회한다(senderToken)
                .response()
                .bodies(OrganizationResponse.class).get(0);

        HeartAssured.request()
                .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken.getAccessToken(),
                        receiverToken.getMemberId())
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

        기본_조직이_생성됨();
        OrganizationResponse organizationResponse = OrganizationAssured.request()
                .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .내_조직을_조회한다(receiverToken)
                .response()
                .bodies(OrganizationResponse.class).get(0);
        ;

        HeartAssured.request()
                .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken.getAccessToken(),
                        receiverToken.getMemberId())
                .마음을_보낸다(organizationResponse.getOrganizationId(), receiverToken.getAccessToken(),
                        senderToken.getMemberId())
                .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken.getAccessToken(),
                        receiverToken.getMemberId())
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

        기본_조직이_생성됨();
        OrganizationResponse organizationResponse = OrganizationAssured.request()
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(senderToken1, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(senderToken2, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .조직에_참여한다(senderToken3, 조직_번호(ORGANIZATION_THANKOO_CODE))
                .내_조직을_조회한다(receiverToken)
                .response()
                .bodies(OrganizationResponse.class).get(0);

        HeartAssured.request()
                .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken1.getAccessToken(),
                        receiverToken.getMemberId())
                .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken2.getAccessToken(),
                        receiverToken.getMemberId())
                .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken3.getAccessToken(),
                        receiverToken.getMemberId())
                .마음을_보낸다(organizationResponse.getOrganizationId(), receiverToken.getAccessToken(),
                        senderToken2.getMemberId())
                .응답_가능한_마음을_조회한다(organizationResponse.getOrganizationId(), receiverToken.getAccessToken())
                .response()
                .status(HttpStatus.OK.value())
                .조회_성공(1, 2);
    }

    @DisplayName("마음을 단건으로 조회할 때 ")
    @Nested
    class HeartExchangeGetTest {

        @DisplayName("주고 받은 내용이 있으면 모두 조회한다.")
        @Test
        void exchangeGet() {
            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .token();

            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .token();

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                    .내_조직을_조회한다(receiverToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            HeartAssured.request()
                    .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken.getAccessToken(),
                            receiverToken.getMemberId())
                    .마음을_보낸다(organizationResponse.getOrganizationId(), receiverToken.getAccessToken(),
                            senderToken.getMemberId())
                    .교환한_마음을_조회한다(organizationResponse.getOrganizationId(), receiverToken.getMemberId(),
                            senderToken.getAccessToken())
                    .response()
                    .조회에_성공한다(senderToken.getMemberId(), receiverToken.getMemberId());
        }

        @DisplayName("받은 내용이 없으면 null을 반환한다.")
        @Test
        void exchangeReceivedNull() {
            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .token();

            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .token();

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_THANKOO_CODE))
                    .내_조직을_조회한다(receiverToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            HeartAssured.request()
                    .마음을_보낸다(organizationResponse.getOrganizationId(), senderToken.getAccessToken(),
                            receiverToken.getMemberId())
                    .교환한_마음을_조회한다(organizationResponse.getOrganizationId(), receiverToken.getMemberId(),
                            senderToken.getAccessToken())
                    .response()
                    .받은_기록이_없으면_null_이다();
        }
    }

}
