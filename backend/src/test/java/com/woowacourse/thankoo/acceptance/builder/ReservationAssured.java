package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.common.exception.dto.ErrorResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.presentation.dto.SimpleReservationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;

public class ReservationAssured {

    private ReservationAssured() {
    }

    public static ReservationRequest 예약_요청(final CouponResponse couponResponse, final Long days) {
        return new ReservationRequest(couponResponse.getCouponId(), LocalDateTime.now().plusDays(days));
    }

    public static ReservationRequest 잘못된_예약_일정_요청(final CouponResponse couponResponse,
                                                  final LocalDateTime localDateTime) {
        return new ReservationRequest(couponResponse.getCouponId(), localDateTime);
    }

    public static ReservationRequestBuilder request() {
        return new ReservationRequestBuilder();
    }

    public static class ReservationRequestBuilder extends RequestBuilder {

        public ReservationRequestBuilder 예약을_요청한다(final String accessToken,
                                                  final ReservationRequest reservationRequest) {
            response = postWithToken("/api/reservations", accessToken, reservationRequest);
            return this;
        }

        public ReservationRequestBuilder 보낸_예약을_조회한다(final String token, final Long organizationId) {
            response = getWithToken("/api/reservations/sent?organization=" + organizationId, token);
            return this;
        }

        public ReservationRequestBuilder 받은_예약을_조회한다(final String token, final Long organizationId) {
            response = getWithToken("/api/reservations/received?organization=" + organizationId, token);
            return this;
        }

        public ReservationRequestBuilder 예약에_응답한다(final String token, final String answer) {
            response = putWithToken(getLocation(), token, new ReservationStatusRequest(answer));
            return this;
        }

        public ReservationRequestBuilder 예약을_취소한다(final String token) {
            response = putWithToken(getLocation() + "/cancel", token);
            return this;
        }

        private String getLocation() {
            String location = response.header(HttpHeaders.LOCATION);
            if (location == null) {
                throw new RuntimeException("request Reservation First");
            }
            return location;
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

        public void 예약이_조회됨(final int size) {
            List<SimpleReservationResponse> simpleReservationResponses = bodies(SimpleReservationResponse.class);

            assertAll(
                    () -> assertThat(simpleReservationResponses).isNotEmpty(),
                    () -> assertThat(simpleReservationResponses).hasSize(size)
            );
        }

        public void 예약_요청_실패됨(final int code) {
            ErrorResponse errorResponse = body(ErrorResponse.class);
            assertThat(errorResponse.getErrorCode()).isEqualTo(code);
        }
    }
}
