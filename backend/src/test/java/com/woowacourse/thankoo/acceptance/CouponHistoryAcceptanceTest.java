package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequest.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequest.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequest.postWithToken;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TYPE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("CouponHistoryAcceptance 는 ")
public class CouponHistoryAcceptanceTest extends AcceptanceTest {

    @DisplayName("유저가 로그인하고 ")
    @Nested
    class LoginAndTest {

        @DisplayName("쿠폰을 보낸다.")
        @Test
        void sendCoupon() {
            TokenResponse senderToken = 토큰을_반환한다(로그인_한다("huni"));
            TokenResponse receiverToken = 토큰을_반환한다(로그인_한다("hoho"));

            CouponRequest couponRequest = new CouponRequest(receiverToken.getMemberId(),
                    new ContentRequest(TYPE, TITLE, MESSAGE));
            ExtractableResponse<Response> response =
                    postWithToken("/api/coupons/send", senderToken.getAccessToken(), couponRequest);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }
}
