package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.쿠폰을_전송한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.쿠폰을_조회한다;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE_OVER;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE_OVER;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.INVALID_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HUNI;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("CouponAcceptance 는 ")
public class CouponAcceptanceTest extends AcceptanceTest {

    @DisplayName("유저가 로그인하고 ")
    @Nested
    class SignInAndTest {

        @DisplayName("쿠폰을 보낼 때 ")
        @Nested
        class SendAndTest {

            @DisplayName("정상적으로 쿠폰을 보낸다.")
            @Test
            void sendCoupon() {
                TokenResponse senderToken = 토큰을_반환한다(로그인_한다(CODE_HUNI));
                TokenResponse receiverToken1 = 토큰을_반환한다(로그인_한다(CODE_SKRR));
                TokenResponse receiverToken2 = 토큰을_반환한다(로그인_한다(CODE_HOHO));

                CouponRequest couponRequest =
                        createCouponRequest(List.of(receiverToken1.getMemberId(), receiverToken2.getMemberId()),
                                TYPE, TITLE, MESSAGE);
                ExtractableResponse<Response> response = 쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            }

            @DisplayName("제목이 제약조건에 맞지 않을 경우 쿠폰 전송 실패한다.")
            @Test
            void sendCouponTitleException() {
                TokenResponse senderToken = 토큰을_반환한다(로그인_한다(CODE_SKRR));
                TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(CODE_HOHO));

                CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE,
                        TITLE_OVER,
                        MESSAGE);
                ExtractableResponse<Response> response = 쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

                assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            }

            @DisplayName("내용이 제약조건에 맞지 않을 경우 쿠폰 전송 실패한다.")
            @Test
            void sendCouponMessageException() {
                TokenResponse senderToken = 토큰을_반환한다(로그인_한다(CODE_SKRR));
                TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(CODE_HOHO));

                CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE,
                        MESSAGE_OVER);
                ExtractableResponse<Response> response = 쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

                assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            }
        }

        @DisplayName("쿠폰을 조회한다.")
        @Test
        void getCoupons() {
            TokenResponse senderToken = 토큰을_반환한다(로그인_한다(CODE_SKRR));
            TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(CODE_HOHO));

            CouponRequest couponRequest1 = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE,
                    MESSAGE);
            CouponRequest couponRequest2 = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE + "1",
                    MESSAGE);
            쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest1);
            쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest2);

            ExtractableResponse<Response> response = 쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED);

            쿠폰이_조회됨(response);
        }
    }

    @DisplayName("로그인 하지 않고 ")
    @Nested
    class NotSignInAndTest {

        @DisplayName("쿠폰을 전송하면 실패한다.")
        @Test
        void sendCouponInvalidToken() {
            TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(CODE_HOHO));

            CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE,
                    MESSAGE);
            ExtractableResponse<Response> response = 쿠폰을_전송한다(INVALID_TOKEN, couponRequest);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        @DisplayName("쿠폰을 조회하면 실패한다.")
        @Test
        void getCouponsInvalidToken() {
            TokenResponse senderToken = 토큰을_반환한다(로그인_한다(CODE_SKRR));
            TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(CODE_HOHO));

            CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE,
                    MESSAGE);
            쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);
            ExtractableResponse<Response> response = 쿠폰을_조회한다(INVALID_TOKEN, NOT_USED);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private void 쿠폰이_조회됨(final ExtractableResponse<Response> response) {
        List<CouponResponse> couponRespons = response.jsonPath()
                .getList(".", CouponResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(couponRespons).isNotEmpty();
    }

    private CouponRequest createCouponRequest(final List<Long> receiverIds,
                                              final String type,
                                              final String title,
                                              final String message) {
        return new CouponRequest(receiverIds, new ContentRequest(type, title, message));
    }
}