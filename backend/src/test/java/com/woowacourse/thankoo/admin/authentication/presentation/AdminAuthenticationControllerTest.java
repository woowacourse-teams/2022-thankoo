package com.woowacourse.thankoo.admin.authentication.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.authentication.application.dto.AdminLoginRequest;
import com.woowacourse.thankoo.admin.authentication.presentation.dto.AdminLoginResponse;
import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminAuthenticationController 는 ")
class AdminAuthenticationControllerTest extends AdminControllerTest {

    @DisplayName("로그인을 하면 토큰을 반환한다.")
    @Test
    void login() throws Exception {
        AdminLoginResponse response = new AdminLoginResponse(1L, "accessToken");
        AdminLoginRequest request = new AdminLoginRequest("name", "password");

        given(adminAuthenticationService.login(any()))
                .willReturn(response);

        ResultActions resultActions = mockMvc.perform(post("/admin/sign-in")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(response)));

        resultActions.andDo(document("admin/authentication/sign-in",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("adminId").type(NUMBER).description("admin id"),
                        fieldWithPath("accessToken").type(STRING).description("JWT access token")
                )));
    }
}
