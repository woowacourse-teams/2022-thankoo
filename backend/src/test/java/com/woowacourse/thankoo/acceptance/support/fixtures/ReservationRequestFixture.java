package com.woowacourse.thankoo.acceptance.support.fixtures;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;

import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ReservationRequestFixture {

    public static ExtractableResponse<Response> 에약을_요청한다(final String token,
                                                         final ReservationRequest reservationRequest) {
        return postWithToken("/api/reservations", token, reservationRequest);
    }

    public static ExtractableResponse<Response> 보낸_예약을_조회한다(final String token) {
        return getWithToken("/api/reservations/sent", token);
    }

    public static ExtractableResponse<Response> 받은_예약을_조회한다(final String token) {
        return getWithToken("/api/reservations/received", token);
    }

    public static ExtractableResponse<Response> 예약을_승인한다(final String reservationId, final String token) {
        return putWithToken("/api/reservations/" + reservationId, token, new ReservationStatusRequest("accept"));
    }
}
