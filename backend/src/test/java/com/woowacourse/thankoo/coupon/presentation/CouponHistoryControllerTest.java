package com.woowacourse.thankoo.coupon.presentation;


import static com.woowacourse.thankoo.common.fixtures.TestFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("CouponHistoryController 는 ")
public class CouponHistoryControllerTest extends ControllerTest {

    @DisplayName("쿠폰을 전송하면 201 CREATED 와 Location 을 반환한다.")
    @Test
    void sendCoupon() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        given(couponHistoryService.save(anyLong(), any(CouponRequest.class)))
                .willReturn(1L);

        ResultActions resultActions = mockMvc.perform(post("/api/coupons/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(new CouponRequest(1L,
                                new ContentRequest(TYPE, TITLE, MESSAGE))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "/api/coupons/1"));

        resultActions.andDo(document("coupon_history/send",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("receiverId").type(NUMBER).description("receiverId"),
                        fieldWithPath("content.couponType").type(STRING).description("couponType"),
                        fieldWithPath("content.title").type(STRING).description("title"),
                        fieldWithPath("content.message").type(STRING).description("message")
                )
        ));
    }
}
