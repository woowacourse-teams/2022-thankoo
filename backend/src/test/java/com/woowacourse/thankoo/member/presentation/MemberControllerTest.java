package com.woowacourse.thankoo.member.presentation;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.createMember;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("MemberController 는 ")
public class MemberControllerTest extends ControllerTest {

    @DisplayName("본인을 제외한 모든 회원을 조회힌다.")
    @Test
    void getMembersExcludeMe() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        List<MemberResponse> memberResponses = List.of(MemberResponse.of(createMember(1L, LALA)),
                MemberResponse.of(createMember(2L, HUNI)));
        given(memberService.getMembersExcludeMe(anyLong()))
                .willReturn(memberResponses);

        ResultActions resultActions = mockMvc.perform(get("/api/members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(memberResponses)));

        resultActions.andDo(document("members/get-members",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].name").type(STRING).description("name"),
                        fieldWithPath("[].socialNickname").type(STRING).description("socialNickname"),
                        fieldWithPath("[].imageUrl").type(STRING).description("imageUrl")
                )));
    }
}
