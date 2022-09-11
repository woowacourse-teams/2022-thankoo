package com.woowacourse.thankoo.admin.member.presentation;


import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.admin.common.AdminControllerTest;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminMemberController 는 ")
public class AdminMemberControllerTest extends AdminControllerTest {

    @DisplayName("모든 회원 정보를 조회힌다.")
    @Test
    void getMembers() throws Exception {
        given(adminMemberService.getMembers())
                .willReturn(List.of(
                        new AdminMemberResponse(1L, LALA_NAME, LALA_EMAIL),
                        new AdminMemberResponse(2L, HOHO_NAME, HOHO_EMAIL)));

        ResultActions resultActions = mockMvc.perform(get("/admin/members"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/members/get-members",
                Preprocessors.preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].name").type(STRING).description("name"),
                        fieldWithPath("[].email").type(STRING).description("email")
                )
        ));
    }
}
