package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpHeaders;

public class ReservationAssured {

    private final ExtractableResponse<Response> response;

    public ReservationAssured(final ExtractableResponse<Response> response) {
        this.response = response;
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

        public ReservationRequestBuilder 보낸_예약을_조회한다(final String token) {
            response = getWithToken("/api/reservations/sent", token);
            return this;
        }

        public ReservationRequestBuilder 받은_예약을_조회한다(final String token) {
            response = getWithToken("/api/reservations/received", token);
            return this;
        }

        public ReservationRequestBuilder 예약에_응답한다(final String token, final String answer) {
            String location = response.header(HttpHeaders.LOCATION);
            if (location == null) {
                throw new RuntimeException("request Reservation First");
            }
            response = putWithToken(location, token, new ReservationStatusRequest(answer));
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

        public void 예약이_조회됨(final int size) {
            List<ReservationResponse> reservationResponses = bodies(ReservationResponse.class);

            assertAll(
                    () -> assertThat(reservationResponses).isNotEmpty(),
                    () -> assertThat(reservationResponses).hasSize(size)
            );
        }
    }
}
