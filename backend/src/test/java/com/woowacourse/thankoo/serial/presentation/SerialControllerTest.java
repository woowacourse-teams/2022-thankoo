package com.woowacourse.thankoo.serial.presentation;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.serial.application.SerialService;
import com.woowacourse.thankoo.serial.application.dto.SerialRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminSerialController 는 ")
@WebMvcTest(SerialController.class)
class SerialControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SerialService serialService;

    @Autowired
    protected ObjectMapper objectMapper;

    @DisplayName("쿠폰 시리얼을 생성한다.")
    @Test
    void saveSerial() throws Exception {
        SerialRequest serialRequest = new SerialRequest("네오", "COFFEE", "1234");

        ResultActions resultActions = mockMvc.perform(post("/admin/serial")
                .content(objectMapper.writeValueAsString(serialRequest))
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
