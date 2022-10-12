package com.woowacourse.thankoo.organization.presentation;

import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.MemberOrganization;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("OrganizationController 는 ")
class OrganizationControllerTest extends ControllerTest {

    @DisplayName("조직에 참여한다.")
    @Test
    void join() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

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

    @DisplayName("회원의 조직을 조회한다.")
    @Test
    void getMemberOrganizations() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

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

    @DisplayName("조직에 접근한다.")
    @Test
    void access() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

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
