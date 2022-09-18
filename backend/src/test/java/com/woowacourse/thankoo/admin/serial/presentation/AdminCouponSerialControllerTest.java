package com.woowacourse.thankoo.admin.serial.presentation;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_2;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_3;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.woowacourse.thankoo.admin.serial.presentation.dto.CouponSerialResponse;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import java.util.List;
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
        CouponSerialRequest couponSerialRequest = new CouponSerialRequest("네오", "COFFEE", SERIAL_1);

        ResultActions resultActions = mockMvc.perform(post("/admin/serial")
                .content(objectMapper.writeValueAsString(couponSerialRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/serial/save-serial",
                Preprocessors.preprocessRequest(prettyPrint()),
                requestFields(
                        fieldWithPath("coachName").type(STRING).description("coach name"),
                        fieldWithPath("couponType").type(STRING).description("coupon type"),
                        fieldWithPath("code").type(STRING).description("serial code")
                )
        ));
    }

    @DisplayName("회원의 id로 쿠폰 시리얼을 조회한다.")
    @Test
    void getByMemberId() throws Exception {
        given(adminCouponSerialQueryService.getByMemberId(anyLong()))
                .willReturn(List.of(
                        new CouponSerialResponse(1L, SERIAL_1, 2L, "네오", "COFFEE"),
                        new CouponSerialResponse(2L, SERIAL_2, 2L, "네오", "COFFEE"),
                        new CouponSerialResponse(3L, SERIAL_3, 2L, "네오", "COFFEE")
                ));

        ResultActions resultActions = mockMvc.perform(get("/admin/serial")
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
