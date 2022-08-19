package com.woowacourse.thankoo.authentication.presentation;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.authentication.application.dto.SignUpRequest;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AuthenticationController 는 ")
class AuthenticationControllerTest extends ControllerTest {

    @DisplayName("최초 로그인 시 200 OK 와 idToken 과 가입정보를 반환한다.")
    @Test
    void signInFirst() throws Exception {
        TokenResponse tokenResponse = new TokenResponse(false, "idToken", null, SKRR_EMAIL);
        given(authenticationService.signIn(anyString()))
                .willReturn(tokenResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/sign-in")
                        .queryParam("code", SKRR_NAME))
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
                        fieldWithPath("joined").type(BOOLEAN).description("가입 여부"),
                        fieldWithPath("accessToken").type(STRING).description("accessToken"),
                        fieldWithPath("memberId").type(NULL).description("null memberId"),
                        fieldWithPath("email").type(STRING).description("email")
                )));
    }

    @DisplayName("기존 회원이 로그인을 요청할 경우 200 OK 와 토큰을 반환한다.")
    @Test
    void signIn() throws Exception {
        TokenResponse tokenResponse = new TokenResponse(true, "accessToken", 1L, SKRR_EMAIL);
        given(authenticationService.signIn(anyString()))
                .willReturn(tokenResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/sign-in")
                        .queryParam("code", SKRR_NAME))
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
                        fieldWithPath("joined").type(BOOLEAN).description("가입 여부"),
                        fieldWithPath("accessToken").type(STRING).description("accessToken"),
                        fieldWithPath("memberId").type(NUMBER).description("memberId"),
                        fieldWithPath("email").type(STRING).description("email")
                )));
    }

    @DisplayName("회원 가입한다.")
    @Test
    void signUp() throws Exception {
        TokenResponse tokenResponse = new TokenResponse(true, "accessToken", 1L, SKRR_EMAIL);
        given(authenticationService.signUp(anyString(), anyString()))
                .willReturn(tokenResponse);

        ResultActions resultActions = mockMvc.perform(post("/api/sign-up")
                        .header(HttpHeaders.AUTHORIZATION, SKRR_TOKEN)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(SKRR_NAME)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().string(objectMapper.writeValueAsString(tokenResponse)));

        resultActions.andDo(document("authentication/sign-up",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("idToken")
                ),
                responseFields(
                        fieldWithPath("joined").type(BOOLEAN).description("가입 여부"),
                        fieldWithPath("accessToken").type(STRING).description("accessToken"),
                        fieldWithPath("memberId").type(NUMBER).description("memberId"),
                        fieldWithPath("email").type(STRING).description("email")
                )));
    }
}
