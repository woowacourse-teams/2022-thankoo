package com.woowacourse.thankoo.member.presentation;

import static com.woowacourse.thankoo.common.fixtures.AuthenticationFixture.ACCESS_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.application.dto.MemberProfileImageRequest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import com.woowacourse.thankoo.member.presentation.dto.ProfileImageUrlResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("MemberController 는 ")
class MemberControllerTest extends ControllerTest {

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMember() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        MemberResponse memberResponse = MemberResponse.of(huni);
        given(memberService.getMember(anyLong())).willReturn(memberResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/members/me")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(memberResponse)));

        resultActions.andDo(document("members/get-member",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("name").type(STRING).description("name"),
                        fieldWithPath("email").type(STRING).description("email"),
                        fieldWithPath("imageUrl").type(STRING).description("imageUrl")
                )));
    }

    @DisplayName("회원 이름을 수정한다.")
    @Test
    void updateName() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        MemberNameRequest memberNameRequest = new MemberNameRequest(LALA_NAME);
        doNothing().when(memberService).updateMemberName(anyLong(), any(MemberNameRequest.class));

        ResultActions resultActions = mockMvc.perform(put("/api/members/me/name")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberNameRequest)))
                .andDo(print())
                .andExpect(
                        status().isNoContent());

        resultActions.andDo(document("members/update-member-name",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("name").type(STRING).description("name")
                )));
    }

    @DisplayName("회원 프로필 이미지를 수정한다.")
    @Test
    void updateProfileImage() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        MemberProfileImageRequest memberProfileImageRequest = new MemberProfileImageRequest(SKRR_IMAGE_URL);
        doNothing().when(memberService).updateMemberProfileImage(anyLong(), any(MemberProfileImageRequest.class));

        ResultActions resultActions = mockMvc.perform(put("/api/members/me/profile-image")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberProfileImageRequest)))
                .andDo(print())
                .andExpect(
                        status().isNoContent());

        resultActions.andDo(document("members/update-member-profile-image",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("imageUrl").type(STRING).description("imageUrl")
                )));
    }

    @DisplayName("모든 회원 프로필 이미지들을 조회한다.")
    @Test
    void getProfileImages() throws Exception {
        List<ProfileImageUrlResponse> responses = Stream.of(SKRR_IMAGE_URL, HUNI_IMAGE_URL)
                .map(ProfileImageUrlResponse::of)
                .collect(Collectors.toList());
        given(memberService.getProfileImages()).willReturn(responses);

        ResultActions resultActions = mockMvc.perform(get("/api/members/profile-images")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(
                        status().isOk());

        resultActions.andDo(document("members/get-member-profile-images",
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("[].imageUrl").type(STRING).description("imageUrl")
                )));
    }
}
