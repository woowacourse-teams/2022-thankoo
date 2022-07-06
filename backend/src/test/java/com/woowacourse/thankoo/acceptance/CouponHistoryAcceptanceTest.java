package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponHistoryRequestFixture.쿠폰을_전송한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponHistoryRequestFixture.쿠폰을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponHistoryRequestFixture.쿠폰이_추가됨;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TYPE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponHistoryResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
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
            TokenResponse senderToken = 토큰을_반환한다(로그인_한다(HUNI_NAME));
            TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(HOHO_NAME));

            CouponRequest couponRequest = createCouponRequest(receiverToken.getMemberId(), TYPE, TITLE, MESSAGE);
            ExtractableResponse<Response> response = 쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        }

        @DisplayName("쿠폰을 조회한다.")
        @Test
        void getCoupons() {
            TokenResponse senderToken = 토큰을_반환한다(로그인_한다(HUNI_NAME));
            TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(HOHO_NAME));

            CouponRequest couponRequest1 = createCouponRequest(receiverToken.getMemberId(), TYPE, TITLE, MESSAGE);
            CouponRequest couponRequest2 = createCouponRequest(receiverToken.getMemberId(), TYPE, TITLE + "1", MESSAGE);
            Long couponHistoryId1 = 쿠폰이_추가됨(쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest1));
            Long couponHistoryId2 = 쿠폰이_추가됨(쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest2));
            ExtractableResponse<Response> response = 쿠폰을_조회한다(receiverToken.getAccessToken());

            쿠폰이_조회됨(couponHistoryId2, couponHistoryId1, response);
        }
    }

    private void 쿠폰이_조회됨(final Long couponHistoryId1,
                         final Long couponHistoryId2,
                         final ExtractableResponse<Response> response) {
        List<Long> couponHistoryIds = response.jsonPath()
                .getList(".", CouponHistoryResponse.class)
                .stream()
                .map(CouponHistoryResponse::getCouponHistoryId)
                .collect(Collectors.toList());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(couponHistoryIds).containsExactly(couponHistoryId1, couponHistoryId2);
    }

    private CouponRequest createCouponRequest(final Long receiverId,
                                              final String type,
                                              final String title,
                                              final String message) {
        return new CouponRequest(receiverId, new ContentRequest(type, title, message));
    }
}
