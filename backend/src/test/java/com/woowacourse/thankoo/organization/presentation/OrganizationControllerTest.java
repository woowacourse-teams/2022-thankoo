package com.woowacourse.thankoo.organization.presentation;

import static com.woowacourse.thankoo.common.fixtures.AuthenticationFixture.ACCESS_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.MemberOrganization;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationMember;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.domain.SimpleOrganization;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationMemberResponse;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import com.woowacourse.thankoo.organization.presentation.dto.SimpleOrganizationResponse;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("OrganizationController 는 ")
class OrganizationControllerTest extends ControllerTest {

    @DisplayName("조직에 참여한다.")
    @Test
    void join() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        OrganizationJoinRequest organizationJoinRequest = new OrganizationJoinRequest(ORGANIZATION_WOOWACOURSE_CODE);
        doNothing().when(organizationService).join(anyLong(), any(OrganizationJoinRequest.class));

        ResultActions resultActions = mockMvc.perform(post("/api/organizations/join")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(organizationJoinRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("organizations/join",
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("code").type(STRING).description("code")
                )
        ));
    }

    @DisplayName("코드로 조직을 조회한다.")
    @Test
    void getOrganizationByCode() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        SimpleOrganizationResponse response = SimpleOrganizationResponse.from(
                new SimpleOrganization(1L, ORGANIZATION_THANKOO));

        given(organizationQueryService.getOrganizationByCode(anyString())).willReturn(response);

        ResultActions resultActions = mockMvc.perform(get("/api/organizations/THANKOO1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("organizations/get-organization-by-code",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("organizationId").type(NUMBER).description("organizationId"),
                        fieldWithPath("name").type(STRING).description("name")
                )
        ));
    }

    @DisplayName("회원의 조직을 조회한다.")
    @Test
    void getMemberOrganizations() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        List<OrganizationResponse> organizationResponses = List.of(
                OrganizationResponse.from(
                        new MemberOrganization(1L,
                                ORGANIZATION_THANKOO,
                                ORGANIZATION_THANKOO_CODE,
                                1,
                                true)),
                OrganizationResponse.from(
                        new MemberOrganization(2L,
                                ORGANIZATION_WOOWACOURSE,
                                ORGANIZATION_WOOWACOURSE_CODE,
                                2,
                                false)
                )
        );

        given(organizationQueryService.getMemberOrganizations(anyLong())).willReturn(organizationResponses);

        ResultActions resultActions = mockMvc.perform(get("/api/organizations/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("organizations/get-my-organizations",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("[].organizationId").type(NUMBER).description("id"),
                        fieldWithPath("[].organizationName").type(STRING).description("organizationName"),
                        fieldWithPath("[].organizationCode").type(STRING).description("organizationCode"),
                        fieldWithPath("[].orderNumber").type(NUMBER).description("orderName"),
                        fieldWithPath("[].lastAccessed").type(BOOLEAN).description("lastAccessed")
                )
        ));
    }

    @DisplayName("본인을 제외한 모든 회원을 조회힌다.")
    @Test
    void getMembersExcludeMe() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        OrganizationValidator organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
        Organization organization = createDefaultOrganization(organizationValidator);

        List<OrganizationMemberResponse> responses = List.of(
                OrganizationMemberResponse.from(new OrganizationMember(huni, organization, 1, true)),
                OrganizationMemberResponse.from(new OrganizationMember(lala, organization, 1, true))
        );

        given(organizationQueryService.getOrganizationMembersExcludeMe(anyLong(), anyLong())).willReturn(responses);

        ResultActions resultActions = mockMvc.perform(get("/api/organizations/1/members")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(responses)));

        resultActions.andDo(document("organizations/get-members",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].name").type(STRING).description("name"),
                        fieldWithPath("[].email").type(STRING).description("email"),
                        fieldWithPath("[].imageUrl").type(STRING).description("imageUrl")
                )));
    }

    @DisplayName("조직에 접근한다.")
    @Test
    void access() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");

        doNothing().when(organizationService).access(anyLong(), anyLong());

        ResultActions resultActions = mockMvc.perform(put("/api/organizations/1/access")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("organizations/access",
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                )
        ));
    }
}
