package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.createCouponRequest;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.받은_쿠폰을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.쿠폰을_전송한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.에약을_요청한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.예약을_승인한다;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
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

    @DisplayName("요청된 예약을 승인한다.")
    @Test
    void updateStatusAccept() {
        TokenResponse senderToken = 토큰을_반환한다(로그인_한다(CODE_SKRR));
        TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(CODE_HOHO));

        CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE,
                MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);
        CouponResponse couponResponse = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(0);

        String reservationId = 에약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse.getCouponId(), LocalDateTime.now().plusDays(1L)))
                .header(HttpHeaders.LOCATION).split("reservations/")[1];

        ExtractableResponse<Response> response = 예약을_승인한다(reservationId, senderToken.getAccessToken());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
