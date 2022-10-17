package com.woowacourse.thankoo.admin.organization.presentaion;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.NUMBER;
import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequest;
import com.woowacourse.thankoo.admin.organization.presentaion.dto.AdminGetOrganizationResponse;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationCode;
import com.woowacourse.thankoo.organization.domain.OrganizationName;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminOrganizationController 는 ")
class AdminOrganizationControllerTest extends AdminControllerTest {

    @DisplayName("조직을 생성한다.")
    @Test
    void createOrganization() throws Exception {
        given(tokenDecoder.decode(anyString()))
                .willReturn("1");
        AdminOrganizationCreationRequest organizationCreationRequest = new AdminOrganizationCreationRequest("newOrg",
                40);

        ResultActions resultActions = mockMvc.perform(post("/admin/organizations")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(organizationCreationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/organization/create-organization",
                Preprocessors.preprocessRequest(prettyPrint()),
                requestFields(
                        fieldWithPath("name").type(STRING).description("organization name"),
                        fieldWithPath("limitedSize").type(NUMBER).description("organization limit size")
                )
        ));
    }

    @DisplayName("조직을 조회한다.")
    @Test
    void getOrganizations() throws Exception {
        given(tokenDecoder.decode(anyString()))
                .willReturn("1");

        AdminGetOrganizationResponse responseElement1 = AdminGetOrganizationResponse.from(
                new Organization(1L, new OrganizationName("땡쿠"), new OrganizationCode("orgCode1"), 15,
                        new OrganizationMembers(List.of())));
        AdminGetOrganizationResponse responseElement2 = AdminGetOrganizationResponse.from(
                new Organization(2L, new OrganizationName("우아코스"), new OrganizationCode("orgCode2"), 15,
                        new OrganizationMembers(List.of())));
        given(adminOrganizationService.getOrganizations(any()))
                .willReturn(List.of(responseElement1, responseElement2));

        ResultActions resultActions = mockMvc.perform(get("/admin/organizations")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .queryParam("startDate", "2022-01-01")
                        .queryParam("endDate", "2022-12-31"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/organization/get-organizations",
                Preprocessors.preprocessResponse(prettyPrint()),
                requestParameters(
                        parameterWithName("startDate").description("startDate"),
                        parameterWithName("endDate").description("endDate")
                ),
                responseFields(
                        fieldWithPath("[].organizationId").type(NUMBER).description("organizationId"),
                        fieldWithPath("[].name").type(STRING).description("name"),
                        fieldWithPath("[].code").type(STRING).description("code"),
                        fieldWithPath("[].limitedSize").type(NUMBER).description("limitedSize"),
                        fieldWithPath("[].createdAt").type(STRING).description("createdAt"),
                        fieldWithPath("[].modifiedAt").type(STRING).description("modifiedAt")
                )
        ));
    }
}
