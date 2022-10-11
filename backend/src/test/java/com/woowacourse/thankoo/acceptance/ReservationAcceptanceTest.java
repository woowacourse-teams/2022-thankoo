package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.CouponAssured.쿠폰_요청;
import static com.woowacourse.thankoo.acceptance.builder.OrganizationAssured.조직_번호;
import static com.woowacourse.thankoo.acceptance.builder.ReservationAssured.예약_요청;
import static com.woowacourse.thankoo.acceptance.builder.ReservationAssured.잘못된_예약_일정_요청;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.ACCEPT;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.DENY;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.CouponAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.acceptance.builder.ReservationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("ReservationAcceptance 는 ")
class ReservationAcceptanceTest extends AcceptanceTest {

    @DisplayName("사용되지 않은 쿠폰으로 예약을 요청한다.")
    @Test
    void reserve() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        CouponResponse couponResponse = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class).get(0);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                .response()
                .status(HttpStatus.CREATED.value());
    }

    @DisplayName("예약 요청 시간이 현재 시간 이전일 경우 요청 에러가 발생한다.")
    @Test
    void reserveTimeFail() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        CouponResponse couponResponse = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class).get(0);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(),
                        잘못된_예약_일정_요청(couponResponse, LocalDateTime.now().minusDays(1L)))
                .response()
                .status(HttpStatus.BAD_REQUEST.value())
                .예약_요청_실패됨(9001);
    }

    @DisplayName("예약 요청 시간의 연도가 현재와 다를경우 요청 에러가 발생한다.")
    @Test
    void reserveYearFail() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        CouponResponse couponResponse = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class).get(0);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(),
                        잘못된_예약_일정_요청(couponResponse, LocalDateTime.now().plusYears(1L)))
                .response()
                .status(HttpStatus.BAD_REQUEST.value())
                .예약_요청_실패됨(9001);
    }

    @DisplayName("회원이 받은 쿠폰으로 보낸 예약을 조회한다.")
    @Test
    void getSentReservations() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        List<CouponResponse> couponResponses = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(0), 1L))
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(1), 1L))
                .보낸_예약을_조회한다(receiverToken.getAccessToken())
                .response()
                .status(HttpStatus.OK.value())
                .예약이_조회됨(2);
    }

    @DisplayName("회원이 보낸 쿠폰으로 받은 예약을 조회한다.")
    @Test
    void getReceivedReservations() {
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
                .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(0), 1L))
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(1), 1L))
                .받은_예약을_조회한다(senderToken.getAccessToken(), organizationId)
                .response()
                .status(HttpStatus.OK.value())
                .예약이_조회됨(2);
    }

    @DisplayName("요청된 예약을 승인한다.")
    @Test
    void updateStatusAccept() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        CouponResponse couponResponse = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class).get(0);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                .response()
                .status(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("요청된 예약을 거절한다.")
    @Test
    void updateStatusDeny() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        CouponResponse couponResponse = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class).get(0);

        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                .예약에_응답한다(senderToken.getAccessToken(), DENY)
                .response()
                .status(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("예약을 요청한 멤버가 예약을 취소한다.")
    @Test
    void cancel() {
        TokenResponse senderToken = AuthenticationAssured.request()
                .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                .로그인_한다(CODE_SKRR)
                .token();

        TokenResponse receiverToken = AuthenticationAssured.request()
                .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                .token();

        CouponResponse couponResponse = CouponAssured.request()
                .쿠폰을_전송한다(senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                .받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED)
                .response()
                .bodies(CouponResponse.class).get(0);
        ReservationAssured.request()
                .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                .예약을_취소한다(receiverToken.getAccessToken())
                .response()
                .status(HttpStatus.NO_CONTENT.value());
    }
}
