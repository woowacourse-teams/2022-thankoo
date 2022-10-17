package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.CouponAssured.쿠폰_요청;
import static com.woowacourse.thankoo.acceptance.builder.OrganizationAssured.조직_번호;
import static com.woowacourse.thankoo.acceptance.builder.ReservationAssured.예약_요청;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.ACCEPT;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.CouponAssured;
import com.woowacourse.thankoo.acceptance.builder.MeetingAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.acceptance.builder.ReservationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("MeetingAcceptance 는 ")
class MeetingAcceptanceTest extends AcceptanceTest {

    @DisplayName("예약이 승인되어 진행중인 미팅을 조회한다.")
    @Test
    void getMeetingsOnProgress() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        기본_조직이_생성됨();
        OrganizationResponse organizationResponse = OrganizationAssured.request()
                .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .내_조직을_조회한다(senderToken)
                .response()
                .bodies(OrganizationResponse.class).get(0);

        Long organizationId = organizationResponse.getOrganizationId();

        List<CouponResponse> couponResponses = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(organizationId, receiverToken.getMemberId()))
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(organizationId, receiverToken.getMemberId()))
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(organizationId, receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class);

        for (CouponResponse couponResponse : couponResponses) {
            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();
        }

        MeetingAssured.request()
                .미팅을_조회한다(receiverToken.getAccessToken(), organizationId)
                .response()
                .status(HttpStatus.OK.value())
                .미팅이_조회됨(3);
    }

    @DisplayName("미팅의 참여자가 미팅을 완료한다.")
    @Test
    void completeMeeting() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        기본_조직이_생성됨();
        OrganizationResponse organizationResponse = OrganizationAssured.request()
                .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                .내_조직을_조회한다(senderToken)
                .response()
                .bodies(OrganizationResponse.class).get(0);

        Long organizationId = organizationResponse.getOrganizationId();

        CouponResponse couponResponse = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(organizationId, receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class)
                .get(0);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                .done();

        MeetingAssured.request()
                .미팅을_조회한다(receiverToken.getAccessToken(), organizationId)
                .미팅을_완료한다(senderToken.getAccessToken())
                .response()
                .status(HttpStatus.NO_CONTENT.value());
    }
}
