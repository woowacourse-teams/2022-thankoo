package com.woowacourse.thankoo.admin.serial.presentation;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.NUMBER;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_2;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_3;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.admin.serial.application.dto.AdminCouponSerialRequest;
import com.woowacourse.thankoo.admin.serial.presentation.dto.AdminCouponSerialResponse;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminSerialController 는 ")
@WebMvcTest(AdminCouponSerialController.class)
class AdminCouponSerialControllerTest extends AdminControllerTest {

    @DisplayName("쿠폰 시리얼을 생성한다.")
    @Test
    void saveSerial() throws Exception {
        given(tokenDecoder.decode(anyString()))
                .willReturn("1");
        AdminCouponSerialRequest couponSerialRequest = new AdminCouponSerialRequest(
                1L, "COFFEE", 5, NEO_TITLE, NEO_MESSAGE);

        ResultActions resultActions = mockMvc.perform(post("/admin/serial")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(couponSerialRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/serial/save-serial",
                Preprocessors.preprocessRequest(prettyPrint()),
                requestFields(
                        fieldWithPath("memberId").type(STRING).description("coach id"),
                        fieldWithPath("couponType").type(STRING).description("coupon type"),
                        fieldWithPath("quantity").type(NUMBER).description("quantity"),
                        fieldWithPath("title").type(NUMBER).description("title"),
                        fieldWithPath("message").type(NUMBER).description("message")
                )
        ));
    }

    @DisplayName("회원의 id로 쿠폰 시리얼을 조회한다.")
    @Test
    void getByMemberId() throws Exception {
        given(tokenDecoder.decode(anyString()))
                .willReturn("1");
        given(adminCouponSerialQueryService.getByMemberId(anyLong()))
                .willReturn(List.of(
                        new AdminCouponSerialResponse(1L, SERIAL_1, 2L, NEO_NAME, "COFFEE"),
                        new AdminCouponSerialResponse(2L, SERIAL_2, 2L, NEO_NAME, "COFFEE"),
                        new AdminCouponSerialResponse(3L, SERIAL_3, 2L, NEO_NAME, "COFFEE")
                ));

        ResultActions resultActions = mockMvc.perform(get("/admin/serial")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .param("memberId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/serial/get-serial",
                Preprocessors.preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].id").type(STRING).description("id"),
                        fieldWithPath("[].code").type(STRING).description("code"),
                        fieldWithPath("[].senderId").type(STRING).description("senderId"),
                        fieldWithPath("[].senderName").type(STRING).description("senderName"),
                        fieldWithPath("[].couponType").type(STRING).description("couponType")
                )
        ));
    }
}
