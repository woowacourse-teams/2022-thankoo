package com.woowacourse.thankoo.authentication.presentation;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AuthenticationController 는 ")
class AuthenticationControllerTest extends ControllerTest {

    @DisplayName("SignIn 시 200 OK 와 토큰을 반환한다.")
    @Test
    void signIn() throws Exception {
        TokenResponse tokenResponse = new TokenResponse("accessToken", 1L);
        given(authenticationService.signIn(anyString()))
                .willReturn(tokenResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/sign-in")
                        .queryParam("code", "skrrr"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(tokenResponse)));

        resultActions.andDo(document("authentication/sign-in",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestParameters(
                        parameterWithName("code").description("code")
                ),
                responseFields(
                        fieldWithPath("accessToken").type(STRING).description("accessToken"),
                        fieldWithPath("memberId").type(NUMBER).description("memberId")
                )));
    }
}
