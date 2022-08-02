package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;

public class CouponAssured {

    private final ExtractableResponse<Response> response;

    public CouponAssured(final ExtractableResponse<Response> response) {
        this.response = response;
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
            response = getWithToken("api/coupons/" + couponId, accessToken);
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

        public void 단건_쿠폰이_조회됨() {
            CouponDetailResponse couponDetailResponse = body(CouponDetailResponse.class);
            assertAll(
                    () -> assertThat(couponDetailResponse.getCoupon()).isNotNull(),
                    () -> assertThat(couponDetailResponse.getTime()).isNotNull()
            );
        }
    }
}
