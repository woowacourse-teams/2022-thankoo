package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.회원가입_후_로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.createCouponRequest;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.받은_쿠폰을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.쿠폰을_전송한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.받은_예약을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.보낸_예약을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.예약을_승인한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.예약을_요청한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.예약을_취소한다;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("ReservationAcceptance 는 ")
class ReservationAcceptanceTest extends AcceptanceTest {

    @DisplayName("사용되지 않은 쿠폰으로 예약을 요청한다.")
    @Test
    void reserve() {
        TokenResponse senderToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_SKRR, SKRR_TOKEN, SKRR_NAME));
        TokenResponse receiverToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_HOHO, HOHO_TOKEN, HOHO_NAME));

        CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

        CouponResponse couponResponse = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(0);

        ExtractableResponse<Response> response = 예약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse.getCouponId(), LocalDateTime.now().plusDays(1L)));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("회원이 받은 쿠폰으로 보낸 예약을 조회한다.")
    @Test
    void getSentReservations() {
        TokenResponse senderToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_SKRR, SKRR_TOKEN, SKRR_NAME));
        TokenResponse receiverToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_HOHO, HOHO_TOKEN, HOHO_NAME));

        CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

        CouponResponse couponResponse1 = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(0);
        CouponResponse couponResponse2 = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(1);

        예약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse1.getCouponId(), LocalDateTime.now().plusDays(1L)));
        예약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse2.getCouponId(), LocalDateTime.now().plusDays(1L)));

        ExtractableResponse<Response> response = 보낸_예약을_조회한다(receiverToken.getAccessToken());

        예약이_조회됨(response);
    }

    @DisplayName("회원이 보낸 쿠폰으로 받은 예약을 조회한다.")
    @Test
    void getReceivedReservations() {
        TokenResponse senderToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_SKRR, SKRR_TOKEN, SKRR_NAME));
        TokenResponse receiverToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_HOHO, HOHO_TOKEN, HOHO_NAME));

        CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

        CouponResponse couponResponse1 = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(0);
        CouponResponse couponResponse2 = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(1);

        예약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse1.getCouponId(), LocalDateTime.now().plusDays(1L)));
        예약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse2.getCouponId(), LocalDateTime.now().plusDays(1L)));

        ExtractableResponse<Response> response = 받은_예약을_조회한다(senderToken.getAccessToken());

        예약이_조회됨(response);
    }

    @DisplayName("요청된 예약을 승인한다.")
    @Test
    void updateStatusAccept() {
        TokenResponse senderToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_SKRR, SKRR_TOKEN, SKRR_NAME));
        TokenResponse receiverToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_HOHO, HOHO_TOKEN, HOHO_NAME));

        CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

        CouponResponse couponResponse = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(0);

        String reservationId = 예약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse.getCouponId(), LocalDateTime.now().plusDays(1L)))
                .header(HttpHeaders.LOCATION).split("reservations/")[1];

        ExtractableResponse<Response> response = 예약을_승인한다(reservationId, senderToken.getAccessToken());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("예약을 요청한 멤버가 예약을 취소한다.")
    @Test
    void cancel() {
        TokenResponse senderToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_SKRR, SKRR_TOKEN, SKRR_NAME));
        TokenResponse receiverToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_HOHO, HOHO_TOKEN, HOHO_NAME));

        CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

        CouponResponse couponResponse = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(0);

        String reservationId = 예약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse.getCouponId(), LocalDateTime.now().plusDays(1L)))
                .header(HttpHeaders.LOCATION).split("reservations/")[1];

        ExtractableResponse<Response> response = 예약을_취소한다(reservationId, receiverToken.getAccessToken());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 예약이_조회됨(final ExtractableResponse<Response> response) {
        List<ReservationResponse> reservationResponses = response.jsonPath()
                .getList(".", ReservationResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(reservationResponses).isNotEmpty();
    }
}
