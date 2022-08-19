package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponTotalResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;

public class CouponAssured {

    private CouponAssured() {
    }

    public static CouponRequest createCouponRequest(final List<Long> receiverIds,
                                                    final String type,
                                                    final String title,
                                                    final String message) {
        return new CouponRequest(receiverIds, new ContentRequest(type, title, message));
    }

    public static CouponRequest 쿠폰_요청(final Long... ids) {
        return createCouponRequest(Arrays.asList(ids), TYPE, TITLE, MESSAGE);
    }

    public static CouponRequest 잘못된_쿠폰_요청(final String type,
                                          final String title,
                                          final String message,
                                          final Long... ids) {
        return createCouponRequest(Arrays.asList(ids), type, title, message);
    }

    public static CouponRequestBuilder request() {
        return new CouponRequestBuilder();
    }

    public static class CouponRequestBuilder extends RequestBuilder {

        public CouponRequestBuilder 쿠폰을_전송한다(final String accessToken, final CouponRequest couponRequest) {
            response = postWithToken("/api/coupons/send", accessToken, couponRequest);
            return this;
        }

        public CouponRequestBuilder 받은_쿠폰을_조회한다(final String accessToken, final String status) {
            response = getWithToken("/api/coupons/received?status=" + status, accessToken);
            return this;
        }

        public CouponRequestBuilder 보낸_쿠폰을_조회한다(final String accessToken) {
            response = getWithToken("/api/coupons/sent", accessToken);
            return this;
        }

        public CouponRequestBuilder 쿠폰_단건_정보를_조회한다(final Long couponId, final String accessToken) {
            response = getWithToken("/api/coupons/" + couponId, accessToken);
            return this;
        }

        public CouponRequestBuilder 주고_받은_쿠폰_개수를_조회한다(final String accessToken) {
            response = getWithToken("/api/coupons/count", accessToken);
            return this;
        }

        public CouponResponseBuilder response() {
            return new CouponResponseBuilder(response);
        }
    }

    public static class CouponResponseBuilder extends ResponseBuilder {

        public CouponResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public CouponResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }

        public void 쿠폰이_조회됨(final int size) {
            List<CouponResponse> responses = bodies(CouponResponse.class);
            assertAll(
                    () -> assertThat(responses).isNotEmpty(),
                    () -> assertThat(responses).hasSize(size)
            );
        }

        public void 단건_쿠폰과_예약이_조회됨() {
            CouponDetailResponse couponDetailResponse = body(CouponDetailResponse.class);
            assertAll(
                    () -> assertThat(couponDetailResponse.getCoupon()).isNotNull(),
                    () -> assertThat(couponDetailResponse.getReservation()).isNotNull(),
                    () -> assertThat(couponDetailResponse.getMeeting()).isNull()
            );
        }

        public void 단건_쿠폰과_미팅이_조회됨() {
            CouponDetailResponse couponDetailResponse = body(CouponDetailResponse.class);
            assertAll(
                    () -> assertThat(couponDetailResponse.getCoupon()).isNotNull(),
                    () -> assertThat(couponDetailResponse.getMeeting()).isNotNull(),
                    () -> assertThat(couponDetailResponse.getReservation()).isNull()
            );
        }

        public void 쿠폰_개수가_조회됨(final int sentCount, final int receivedCount) {
            CouponTotalResponse couponTotalResponse = body(CouponTotalResponse.class);
            assertAll(
                    () -> assertThat(couponTotalResponse.getSentCount()).isEqualTo(sentCount),
                    () -> assertThat(couponTotalResponse.getReceivedCount()).isEqualTo(receivedCount)
            );
        }
    }
}
