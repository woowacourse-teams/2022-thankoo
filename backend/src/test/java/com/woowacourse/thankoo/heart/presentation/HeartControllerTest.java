package com.woowacourse.thankoo.heart.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class HeartControllerTest extends ControllerTest {

    @DisplayName("마음을 전송하면 200 OK 를 반환한다.")
    @Test
    void send() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        doNothing().when(heartService).send(anyLong(), any(HeartRequest.class));

        ResultActions resultActions = mockMvc.perform(post("/api/hearts/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(new HeartRequest(1L)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("hearts/send",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("receiverId").type(NUMBER).description("receiverId")
                )
        ));
    }
}
