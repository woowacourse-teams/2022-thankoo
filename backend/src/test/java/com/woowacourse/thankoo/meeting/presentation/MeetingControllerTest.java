package com.woowacourse.thankoo.meeting.presentation;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.meeting.domain.MeetingCoupon;
import com.woowacourse.thankoo.meeting.presentation.dto.SimpleMeetingResponse;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("MeetingController 는 ")
class MeetingControllerTest extends ControllerTest {

    @DisplayName("회원의 미팅을 조회한다.")
    @Test
    void getMeetings() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        List<SimpleMeetingResponse> responses = List.of(
                SimpleMeetingResponse.of(
                        new MeetingCoupon(1L, LocalDateTime.now().plusDays(1L), TimeZoneType.ASIA_SEOUL,
                                CouponType.COFFEE.name(), LALA_NAME)),
                SimpleMeetingResponse.of(
                        new MeetingCoupon(2L, LocalDateTime.now().plusDays(1L), TimeZoneType.ASIA_SEOUL,
                                CouponType.MEAL.name(), HOHO_NAME)),
                SimpleMeetingResponse.of(
                        new MeetingCoupon(3L, LocalDateTime.now().plusDays(1L), TimeZoneType.ASIA_SEOUL,
                                CouponType.COFFEE.name(), SKRR_NAME))
        );
        given(meetingQueryService.findMeetings(anyLong(), anyLong()))
                .willReturn(responses);

        ResultActions resultActions = mockMvc.perform(get("/api/meetings")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .queryParam("organization", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("meetings/get-meetings",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestParameters(
                        parameterWithName("organization").description("organizationId")
                ),
                responseFields(
                        fieldWithPath("[].meetingId").type(NUMBER).description("id"),
                        fieldWithPath("[].time.meetingTime").type(STRING).description("meetingTime"),
                        fieldWithPath("[].time.timeZone").type(STRING).description("timeZone"),
                        fieldWithPath("[].couponType").type(STRING).description("couponType"),
                        fieldWithPath("[].memberName").type(STRING).description("memberName")
                )
        ));
    }

    @DisplayName("회원의 미팅을 완료한다.")
    @Test
    void complete() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        doNothing().when(meetingService).complete(anyLong(), anyLong());

        ResultActions resultActions = mockMvc.perform(put("/api/meetings/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken"))
                .andDo(print())
                .andExpect(status().isNoContent());

        resultActions.andDo(document("meetings/complete",
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                )
        ));
    }
}
