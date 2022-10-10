package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.serial.application.dto.SerialCodeRequest;
import com.woowacourse.thankoo.serial.presentation.dto.CouponSerialResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CouponSerialAssured {

    private CouponSerialAssured() {
    }

    public static SerialCodeRequest 쿠폰_시리얼_요청(final String serialCode) {
        return new SerialCodeRequest(serialCode);
    }

    public static CouponSerialRequestBuilder request() {
        return new CouponSerialRequestBuilder();
    }

    public static class CouponSerialRequestBuilder extends RequestBuilder {

        public CouponSerialAssured.CouponSerialRequestBuilder 쿠폰_시리얼을_조회한다(final String accessToken,
                                                                           final Long organizationId,
                                                                           final String serialCode) {
            response = getWithToken("/api/organizations/" + organizationId +
                            "/coupon-serials?code=" + serialCode, accessToken);
            return this;
        }

        public CouponSerialAssured.CouponSerialRequestBuilder 쿠폰_시리얼을_사용한다(final String accessToken,
                                                                           final SerialCodeRequest request) {
            response = postWithToken("/api/coupon-serials", accessToken, request);
            return this;
        }

        public CouponSerialResponseBuilder response() {
            return new CouponSerialResponseBuilder(response);
        }
    }

    public static class CouponSerialResponseBuilder extends ResponseBuilder {

        public CouponSerialResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public CouponSerialAssured.CouponSerialResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }

        public void 단건_시리얼_쿠폰이_조회됨() {
            CouponSerialResponse couponSerialResponse = body(CouponSerialResponse.class);
            assertAll(
                    () -> assertThat(couponSerialResponse.getId()).isNotNull(),
                    () -> assertThat(couponSerialResponse.getSenderId()).isNotNull(),
                    () -> assertThat(couponSerialResponse.getSenderName()).isNotNull(),
                    () -> assertThat(couponSerialResponse.getCouponType()).isNotNull()
            );
        }
    }
}
