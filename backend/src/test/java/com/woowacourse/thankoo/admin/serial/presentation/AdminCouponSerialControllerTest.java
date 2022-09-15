package com.woowacourse.thankoo.admin.serial.presentation;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
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
        CouponSerialRequest couponSerialRequest = new CouponSerialRequest("네오", "COFFEE", "1234");

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
}
