package com.woowacourse.thankoo.admin.coupon.presentation;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.woowacourse.thankoo.admin.coupon.application.AdminCouponService;
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationContext;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminCouponController 는 ")
@WebMvcTest(AdminCouponController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class AdminCouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminCouponService adminCouponService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationContext authenticationContext;

    @DisplayName("모든 쿠폰을 조회한다.")
    @Test
    void getCoupons() throws Exception {
        AdminMemberResponse lala = new AdminMemberResponse(1L, LALA_NAME, LALA_EMAIL);
        AdminMemberResponse hoho = new AdminMemberResponse(2L, HOHO_NAME, HOHO_EMAIL);
        given(adminCouponService.getCoupons(anyString()))
                .willReturn(List.of(
                        new AdminCouponResponse(1L, lala, hoho, TYPE, TITLE, MESSAGE, "NOT_USED"),
                        new AdminCouponResponse(2L, hoho, lala, TYPE, TITLE, MESSAGE, "RESERVING"))
                );

        ResultActions resultActions = mockMvc.perform(get("/admin/coupons?status=all"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/coupons/get-coupons",
                Preprocessors.preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.email").type(STRING).description("senderEmail"),
                        fieldWithPath("[].receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("[].receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("[].receiver.email").type(STRING).description("receiverEmail"),
                        fieldWithPath("[].type").type(STRING).description("type"),
                        fieldWithPath("[].title").type(STRING).description("title"),
                        fieldWithPath("[].message").type(STRING).description("message"),
                        fieldWithPath("[].status").type(STRING).description("status")
                )
        ));
    }

    @DisplayName("상태에 해당하는 모든 쿠폰을 조회한다.")
    @Test
    void getCouponsByStatus() throws Exception {
        AdminMemberResponse lala = new AdminMemberResponse(1L, LALA_NAME, LALA_EMAIL);
        AdminMemberResponse hoho = new AdminMemberResponse(2L, HOHO_NAME, HOHO_EMAIL);
        given(adminCouponService.getCoupons(anyString()))
                .willReturn(List.of(
                        new AdminCouponResponse(1L, lala, hoho, TYPE, TITLE, MESSAGE, "RESERVING"),
                        new AdminCouponResponse(2L, hoho, lala, TYPE, TITLE, MESSAGE, "RESERVING"))
                );

        ResultActions resultActions = mockMvc.perform(get("/admin/coupons?status=reserving"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/coupons/get-coupons-status",
                Preprocessors.preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.email").type(STRING).description("senderEmail"),
                        fieldWithPath("[].receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("[].receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("[].receiver.email").type(STRING).description("receiverEmail"),
                        fieldWithPath("[].type").type(STRING).description("type"),
                        fieldWithPath("[].title").type(STRING).description("title"),
                        fieldWithPath("[].message").type(STRING).description("message"),
                        fieldWithPath("[].status").type(STRING).description("status")
                )
        ));
    }
}
