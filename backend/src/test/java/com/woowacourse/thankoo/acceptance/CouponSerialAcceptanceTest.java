package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.CouponSerialAssured.쿠폰_시리얼_요청;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.LALA_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.CouponSerialAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("CouponSerialAcceptance 는 ")
class CouponSerialAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원이 로그인하고 ")
    @Nested
    class SignInAndTest {

        @Autowired
        private CouponSerialRepository couponSerialRepository;

        @DisplayName("시리얼 번호를 조회할 때 번호가 존재하면 조회한다.")
        @Test
        void createCoupon() {
            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .token();

            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .token();

            Long senderId = senderToken.getMemberId();

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(receiverToken, new OrganizationJoinRequest(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(senderToken, new OrganizationJoinRequest(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(receiverToken)
                    .response()
                    .bodies(OrganizationResponse.class)
                    .get(0);

            쿠폰_시리얼을_생성한다(organizationResponse.getOrganizationId(), senderId, SERIAL_1);

            CouponSerialAssured.request()
                    .쿠폰_시리얼을_조회한다(receiverToken.getAccessToken(), SERIAL_1)
                    .response()
                    .status(HttpStatus.OK.value())
                    .단건_시리얼_쿠폰이_조회됨();
        }

        @DisplayName("시리얼 번호를 사용할 때 시리얼이 유효하면 시리얼을 사용하고 쿠폰을 생성한다.")
        @Test
        void userSerialCoupon() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(LALA_TOKEN, LALA_NAME)
                    .token();
            Long senderId = senderToken.getMemberId();

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(LALA_TOKEN, LALA_NAME)
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .token();

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, new OrganizationJoinRequest(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, new OrganizationJoinRequest(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(receiverToken)
                    .response()
                    .bodies(OrganizationResponse.class)
                    .get(0);

            쿠폰_시리얼을_생성한다(organizationResponse.getOrganizationId(), senderId, SERIAL_1);

            CouponSerialAssured.request()
                    .쿠폰_시리얼을_사용한다(receiverToken.getAccessToken(), 쿠폰_시리얼_요청(SERIAL_1))
                    .response()
                    .status(HttpStatus.OK.value());
        }

        private CouponSerial 쿠폰_시리얼을_생성한다(Long organizationId, Long memberId, String code) {
            CouponSerial couponSerial = couponSerialRepository
                    .save(new CouponSerial(organizationId, code, memberId, COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
            couponSerialRepository.flush();
            return couponSerial;
        }
    }
}

