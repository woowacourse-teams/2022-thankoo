package com.woowacourse.thankoo.admin.common.qrcode.presentation;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.ARRAY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.admin.qrcode.application.AdminQrCodeService;
import com.woowacourse.thankoo.admin.qrcode.presentation.AdminQrCodeController;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminLinkResponse;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminSerialRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminQrCodeController 는 ")
@WebMvcTest(AdminQrCodeController.class)
class AdminQrCodeControllerTest extends AdminControllerTest {

    @MockBean
    private AdminQrCodeService adminQrCodeService;

    @DisplayName("시리얼 코드로 QR 코드를 가져온다.")
    @Test
    void getCoupons() throws Exception {
        given(adminQrCodeService.getLinks(any()))
                .willReturn(List.of(
                        new AdminLinkResponse("http://test-qrserver/1"),
                        new AdminLinkResponse("http://test-qrserver/2"),
                        new AdminLinkResponse("http://test-qrserver/3")
                ));

        AdminSerialRequest requests = new AdminSerialRequest(List.of("1234", "1235", "1236"));

        ResultActions resultActions = mockMvc.perform(get("/admin/qrcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests)))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/serial/get-link",
                Preprocessors.preprocessRequest(prettyPrint()),
                Preprocessors.preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("serials").type(ARRAY).description("serials")),
                responseFields(
                        fieldWithPath("[].link").type(STRING).description("link")
                )));
    }
}
