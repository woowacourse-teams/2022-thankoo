package com.woowacourse.thankoo.serial.presentation;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.presentation.dto.CouponSerialResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("CouponSerialController 는 ")
class CouponSerialControllerTest extends ControllerTest {

    @DisplayName("시리얼 코드로 쿠폰 시리얼을 조회한다.")
    @Test
    void getCouponSerial() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        given(couponSerialQueryService.getCouponSerialByCode(anyLong(), anyString()))
                .willReturn(new CouponSerialResponse(1L, 2L, NEO_NAME, CouponType.COFFEE.getValue()));

        ResultActions resultActions = mockMvc.perform(get("/api/coupon-serials")
                        .param("code", SERIAL_1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("coupons/get-serial",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestParameters(
                        parameterWithName("code").description("serial code")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("senderId").type(NUMBER).description("sender id"),
                        fieldWithPath("senderName").type(STRING).description("sender name"),
                        fieldWithPath("couponType").type(STRING).description("coupon type")
                )
        ));
    }

    @DisplayName("쿠폰 시리얼을 사용한다.")
    @Test
    void createCouponWithSerial() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        CouponSerialRequest request = new CouponSerialRequest(SERIAL_1);

        ResultActions resultActions = mockMvc.perform(post("/api/coupon-serials")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("coupons/create-with-serial",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("serialCode").type(STRING).description("serial code")
                )
        ));
    }
}
