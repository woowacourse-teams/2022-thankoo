package com.woowacourse.thankoo.admin.member.presentation;


import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.admin.member.application.dto.AdminMemberNameRequest;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminMemberController 는 ")
public class AdminMemberControllerTest extends AdminControllerTest {

    @DisplayName("기간 검색 조건에 따른 모든 회원 정보를 조회힌다.")
    @Test
    void getMembers() throws Exception {
        given(tokenDecoder.decode(anyString()))
                .willReturn("1");
        given(adminMemberService.getMembers(any()))
                .willReturn(List.of(
                        new AdminMemberResponse(1L, LALA_NAME, LALA_EMAIL),
                        new AdminMemberResponse(2L, HOHO_NAME, HOHO_EMAIL)));

        ResultActions resultActions = mockMvc.perform(get("/admin/members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .queryParam("startDate", "2022-01-01")
                        .queryParam("endDate", "2022-12-31"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/members/get-members",
                Preprocessors.preprocessResponse(prettyPrint()),
                requestParameters(
                        parameterWithName("startDate").description("startDate"),
                        parameterWithName("endDate").description("endDate")
                ),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].name").type(STRING).description("name"),
                        fieldWithPath("[].email").type(STRING).description("email")
                )
        ));
    }

    @DisplayName("회원 이름을 변경한다.")
    @Test
    void updateMemberName() throws Exception {
        given(tokenDecoder.decode(anyString()))
                .willReturn("1");
        doNothing().when(adminMemberService).updateMemberName(anyLong(), any());

        ResultActions resultActions = mockMvc.perform(put("/admin/members/23")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AdminMemberNameRequest(LALA_NAME))))
                .andDo(print())
                .andExpect(
                        status().isNoContent());

        resultActions.andDo(document("admin/members/update-member-name",
                Preprocessors.preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("name").type(STRING).description("name")
                ))
        );
    }
}
