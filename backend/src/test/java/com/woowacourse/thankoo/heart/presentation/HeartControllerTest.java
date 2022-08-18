package com.woowacourse.thankoo.heart.presentation;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import com.woowacourse.thankoo.heart.domain.MemberHeart;
import com.woowacourse.thankoo.heart.presentation.dto.ReceivedHeartResponse;
import com.woowacourse.thankoo.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
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

    @DisplayName("응답 가능한 마음만 조회한다.")
    @Test
    void getReceivedHearts() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);
        List<ReceivedHeartResponse> responses = List.of(
                ReceivedHeartResponse.from(new MemberHeart(1L, huni, 1, LocalDateTime.now().minusHours(2L), true)),
                ReceivedHeartResponse.from(new MemberHeart(2L, lala, 3, LocalDateTime.now().minusHours(1L), true))
        );
        given(heartQueryService.getReceivedHeart(anyLong()))
                .willReturn(responses);

        ResultActions resultActions = mockMvc.perform(get("/api/hearts/received")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(responses)));

        resultActions.andDo(document("hearts/received",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("[].heartId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.email").type(STRING).description("sendEmail"),
                        fieldWithPath("[].sender.imageUrl").type(STRING).description("senderImageUrl"),
                        fieldWithPath("[].count").type(NUMBER).description("count"),
                        fieldWithPath("[].receivedAt").type(STRING).description("receivedAt"),
                        fieldWithPath("[].last").type(BOOLEAN).description("last")
                )
        ));
    }
}
