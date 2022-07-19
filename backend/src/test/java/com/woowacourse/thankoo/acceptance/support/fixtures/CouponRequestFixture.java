package com.woowacourse.thankoo.acceptance.support.fixtures;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;

import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CouponRequestFixture {

    public static ExtractableResponse<Response> 쿠폰을_전송한다(final String accessToken,
                                                         final CouponRequest couponRequest) {
        return postWithToken("/api/coupons/send", accessToken, couponRequest);
    }

    public static ExtractableResponse<Response> 받은_쿠폰을_조회한다(final String accessToken, final String status) {
        return getWithToken("/api/coupons/received?status=" + status, accessToken);
    }

    public static ExtractableResponse<Response> 보낸_쿠폰을_조회한다(final String accessToken) {
        return getWithToken("/api/coupons/sent", accessToken);
    }
}
