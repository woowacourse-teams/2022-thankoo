package com.woowacourse.thankoo.serial.presentation;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.serial.presentation.dto.CouponSerialResponse;
import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("CouponSerialController 는 ")
class CouponSerialControllerTest extends ControllerTest {

    @DisplayName("쿠폰 시리얼로 쿠폰을 생성한다.")
    @Test
    void createCouponWithSerial() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        CouponSerialRequest request = new CouponSerialRequest(SERIAL_1);
        given(couponSerialQueryService.getByCode(anyString()))
                .willReturn(new CouponSerialResponse(1L, request.getSerialCode(), 1L, HUNI_NAME,
                        CouponType.COFFEE.getValue()));

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
