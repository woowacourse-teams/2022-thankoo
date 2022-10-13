package com.woowacourse.thankoo.admin.organization.presentaion;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.NUMBER;
import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequest;
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
}
