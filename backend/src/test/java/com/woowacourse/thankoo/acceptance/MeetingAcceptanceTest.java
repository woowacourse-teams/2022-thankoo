package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.회원가입_후_로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.createCouponRequest;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.받은_쿠폰을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.쿠폰을_전송한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.만남을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.예약을_승인한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.예약을_요청한다;
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
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.meeting.presentation.dto.MeetingResponse;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("MeetingAcceptance 는 ")
public class MeetingAcceptanceTest extends AcceptanceTest {

    @DisplayName("예약이 승인되어 진행중인 만남을 조회한다.")
    @Test
    void getMeetingsOnProgress() {
        TokenResponse senderToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_SKRR, SKRR_TOKEN, SKRR_NAME));
        TokenResponse receiverToken = 토큰을_반환한다(회원가입_후_로그인_한다(CODE_HOHO, HOHO_TOKEN, HOHO_NAME));

        CouponRequest couponRequest1 = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        CouponRequest couponRequest2 = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        CouponRequest couponRequest3 = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest1);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest2);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest3);

        List<CouponResponse> couponResponses = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class);

        for (CouponResponse couponResponse : couponResponses) {
            String reservationId = 예약을_요청한다(receiverToken.getAccessToken(),
                    new ReservationRequest(couponResponse.getCouponId(), LocalDateTime.now().plusDays(1L)))
                    .header(HttpHeaders.LOCATION).split("reservations/")[1];
            예약을_승인한다(reservationId, senderToken.getAccessToken());
        }

        ExtractableResponse<Response> response = 만남을_조회한다(receiverToken.getAccessToken());

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getList(".", MeetingResponse.class)).hasSize(3)
        );
    }
}
