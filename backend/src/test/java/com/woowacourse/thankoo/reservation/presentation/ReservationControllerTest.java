package com.woowacourse.thankoo.reservation.presentation;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("ReservationController 는 ")
class ReservationControllerTest extends ControllerTest {

    @DisplayName("예약을 요청한다.")
    @Test
    void reserve() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        ResultActions resultActions = mockMvc.perform(post("/api/reservations")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(new ReservationRequest(1L, LocalDateTime.now())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        resultActions.andDo(document("reservations/reserve",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("startAt").type(STRING).description("startAt")
                )
        ));
    }

    @DisplayName("예약 요청 리스트를 조회한다.")
    @Test
    void getReservations() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        given(reservationService.getReservations(anyLong()))
                .willReturn(List.of(
                        new ReservationResponse(1L, TITLE, TYPE, LocalDateTime.now()),
                        new ReservationResponse(2L, TITLE, TYPE, LocalDateTime.now()),
                        new ReservationResponse(3L, TITLE, TYPE, LocalDateTime.now())
                ));

        ResultActions resultActions = mockMvc.perform(get("/api/reservations")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("reservation/get-reservations",
                responseFields(
                        fieldWithPath("[].reservationId").type(NUMBER).description("reservationId"),
                        fieldWithPath("[].name").type(STRING).description("name"),
                        fieldWithPath("[].couponType").type(STRING).description("couponType"),
                        fieldWithPath("[].meetingTime").type(STRING).description("meetingTime")
                )
        ));
    }

    @DisplayName("예약 요청 상태를 변경한다.")
    @Test
    void updateReservationStatus() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        doNothing().when(reservationService).updateStatus(anyLong(), anyLong(), any(ReservationStatusRequest.class));

        ResultActions resultActions = mockMvc.perform(put("/api/reservations/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ReservationStatusRequest("accept"))))
                .andDo(print())
                .andExpect(
                        status().isNoContent());

        resultActions.andDo(document("reservations/update-reservation-status",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),

                requestFields(
                        fieldWithPath("status").type(STRING).description("status")
                )));
    }
}
