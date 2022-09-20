package com.woowacourse.thankoo.admin.common.qrcode.presentation;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.admin.common.qrcode.infrastructure.QrCodeClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("QrCodeController 는 ")
@WebMvcTest(QrCodeController.class)
class QrCodeControllerTest extends AdminControllerTest {

    @MockBean
    private QrCodeClient qrCodeClient;

    @DisplayName("시리얼 코드로 QR 코드를 가져온다.")
    @Test
    void getCoupons() throws Exception {
        given(qrCodeClient.getQrCode(anyString()))
                .willReturn(new ClassPathResource("/static/thankoo.png"));

        ResultActions resultActions = mockMvc.perform(get("/admin/qrcode?serial=1234"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/serial/get-link",
                Preprocessors.preprocessRequest(prettyPrint()),
                requestParameters(parameterWithName("serial").description("serial code")),
                responseFields(
                        fieldWithPath("[].link").type(NUMBER).description("link")
                )));
    }
}
