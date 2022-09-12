package com.woowacourse.thankoo.admin.reservation.presentation;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.NUMBER;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.reservation.application.AdminReservationService;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationRequest;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationResponse;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminReservationController 는 ")
@WebMvcTest(AdminReservationController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class AdminReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminReservationService reservationService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;


    @DisplayName("모든 예약을 조회한다.")
    @Test
    void getCoupons() throws Exception {
        AdminReservationResponse response = new AdminReservationResponse(1L, LocalDate.now(), LocalDateTime.now(),
                "Asia/Seoul", "WATTING", 2L, 3L, LocalDateTime.now().minusDays(1L), LocalDateTime.now().minusDays(1L));

        given(reservationService.getReservations(any(AdminReservationRequest.class)))
                .willReturn(List.of(
                        new AdminReservationResponse(1L, LocalDate.now(), LocalDateTime.now(),
                                "Asia/Seoul", "WAITING", 2L, 3L, LocalDateTime.now().minusDays(1L),
                                LocalDateTime.now().minusDays(1L)),
                        new AdminReservationResponse(2L, LocalDate.now(), LocalDateTime.now(),
                                "Asia/Seoul", "ACCEPT", 2L, 4L, LocalDateTime.now().minusDays(1L),
                                LocalDateTime.now().minusDays(1L)))
                );

        ResultActions resultActions = mockMvc.perform(
                        get("/admin/reservations", LocalDateTime.now().minusDays(1L), LocalDateTime.now()))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/reservations/get-reservations",
                Preprocessors.preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].date").type(STRING).description("date"),
                        fieldWithPath("[].time").type(STRING).description("time"),
                        fieldWithPath("[].timeZone").type(STRING).description("timeZone"),
                        fieldWithPath("[].status").type(STRING).description("status"),
                        fieldWithPath("[].memberId").type(NUMBER).description("memberId"),
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].createdAt").type(STRING).description("createdAt"),
                        fieldWithPath("[].modifiedAt").type(STRING).description("modifiedAt")
                )
        ));
    }
}