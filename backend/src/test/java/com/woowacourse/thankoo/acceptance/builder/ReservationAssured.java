package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.acceptance.builder.MemberAssured.MemberRequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.MemberAssured.MemberResponseBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ReservationAssured {

    private final ExtractableResponse<Response> response;

    public ReservationAssured(final ExtractableResponse<Response> response) {
        this.response = response;
    }

    public static ReservationRequestBuilder request() {
        return new ReservationRequestBuilder();
    }

    public static class ReservationRequestBuilder extends RequestBuilder {

        public ReservationRequestBuilder 예약을_요청한다(final String accessToken, final ReservationRequest reservationRequest) {
            response = postWithToken("/api/reservations", accessToken, reservationRequest);
            return this;
        }

        public ReservationResponseBuilder response() {
            return new ReservationResponseBuilder(response);
        }
    }

    public static class ReservationResponseBuilder extends ResponseBuilder {

        public ReservationResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public ReservationResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }
    }
}
