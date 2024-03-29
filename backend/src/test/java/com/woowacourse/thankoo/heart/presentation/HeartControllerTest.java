package com.woowacourse.thankoo.heart.presentation;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
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
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.heart.application.dto.HeartSendCommand;
import com.woowacourse.thankoo.heart.domain.Heart;
import com.woowacourse.thankoo.heart.presentation.dto.HeartExchangeResponse;
import com.woowacourse.thankoo.heart.presentation.dto.HeartRequest;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponse;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("HeartController 는 ")
class HeartControllerTest extends ControllerTest {

    @DisplayName("마음을 전송하면 200 OK 를 반환한다.")
    @Test
    void send() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        doNothing().when(heartService).send(any(HeartSendCommand.class));

        ResultActions resultActions = mockMvc.perform(post("/api/hearts/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(new HeartRequest(1L, 1L)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("hearts/send",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("organizationId").type(NUMBER).description("organizationId"),
                        fieldWithPath("receiverId").type(NUMBER).description("receiverId")
                )
        ));
    }

    @DisplayName("응답 가능한 마음만 조회한다.")
    @Test
    void getReceivedHearts() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        Member hoho = new Member(3L, HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(4L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);
        HeartResponses heartResponses = HeartResponses.of(
                List.of(new Heart(1L, 1L, huni.getId(), lala.getId(), 1, true)),
                List.of(new Heart(2L, 1L, hoho.getId(), huni.getId(), 1, true),
                        new Heart(3L, 1L, skrr.getId(), huni.getId(), 1, true)));
        given(heartService.getEachHeartsLast(anyLong(), anyLong()))
                .willReturn(heartResponses);

        ResultActions resultActions = mockMvc.perform(get("/api/hearts/me")
                        .queryParam("organization", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(heartResponses)));

        resultActions.andDo(document("hearts/received",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestParameters(
                        parameterWithName("organization").description("organizationId")
                ),
                responseFields(
                        fieldWithPath("sent.[].heartId").type(NUMBER).description("sentHeartId"),
                        fieldWithPath("sent.[].senderId").type(NUMBER).description("sentSenderId"),
                        fieldWithPath("sent.[].receiverId").type(NUMBER).description("sentReceiverId"),
                        fieldWithPath("sent.[].count").type(NUMBER).description("sentCount"),
                        fieldWithPath("sent.[].last").type(BOOLEAN).description("sentLast"),
                        fieldWithPath("sent.[].modifiedAt").type(NULL).description("sentModifiedAt"),
                        fieldWithPath("received.[].heartId").type(NUMBER).description("receivedHeartId"),
                        fieldWithPath("received.[].senderId").type(NUMBER).description("receivedSenderId"),
                        fieldWithPath("received.[].receiverId").type(NUMBER).description("receivedReceiverId"),
                        fieldWithPath("received.[].count").type(NUMBER).description("receivedCount"),
                        fieldWithPath("received.[].last").type(BOOLEAN).description("receivedLast"),
                        fieldWithPath("received.[].modifiedAt").type(NULL).description("receivedModifiedAt")
                )
        ));
    }

    @DisplayName("마음을 단건 조회한다.")
    @Test
    void getHeart() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member lala = new Member(5L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);

        HeartResponse send = HeartResponse.from(new Heart(1L, 1L, huni.getId(), lala.getId(), 1, false));
        HeartResponse receive = HeartResponse.from(new Heart(2L, 1L, lala.getId(), huni.getId(), 1, true));

        HeartExchangeResponse heartExchangeResponse = new HeartExchangeResponse(send, receive);
        given(heartService.getSentReceivedHeart(any()))
                .willReturn(heartExchangeResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/hearts")
                        .queryParam("organization", "1")
                        .queryParam("receiver", "5")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(heartExchangeResponse)));

        resultActions.andDo(document("hearts/heart",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestParameters(
                        parameterWithName("organization").description("organizationId"),
                        parameterWithName("receiver").description("receiverId")
                ),
                responseFields(
                        fieldWithPath("sent.heartId").type(NUMBER).description("sentHeartId"),
                        fieldWithPath("sent.senderId").type(NUMBER).description("sentSenderId"),
                        fieldWithPath("sent.receiverId").type(NUMBER).description("sentReceiverId"),
                        fieldWithPath("sent.count").type(NUMBER).description("sentCount"),
                        fieldWithPath("sent.last").type(BOOLEAN).description("sentLast"),
                        fieldWithPath("sent.modifiedAt").type(NULL).description("sentModifiedAt"),
                        fieldWithPath("received.heartId").type(NUMBER).description("receivedHeartId"),
                        fieldWithPath("received.senderId").type(NUMBER).description("receivedSenderId"),
                        fieldWithPath("received.receiverId").type(NUMBER).description("receivedReceiverId"),
                        fieldWithPath("received.count").type(NUMBER).description("receivedCount"),
                        fieldWithPath("received.last").type(BOOLEAN).description("receivedLast"),
                        fieldWithPath("received.modifiedAt").type(NULL).description("receivedModifiedAt")
                )
        ));
    }
}
