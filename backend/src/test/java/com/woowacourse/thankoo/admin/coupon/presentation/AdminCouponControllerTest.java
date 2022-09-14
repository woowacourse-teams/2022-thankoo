package com.woowacourse.thankoo.admin.coupon.presentation;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static org.mockito.ArgumentMatchers.any;
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
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AdminCouponController 는 ")
class AdminCouponControllerTest extends AdminControllerTest {

    @DisplayName("기간에 해당하는 모든 쿠폰을 조회한다.")
    @Test
    void getCoupons() throws Exception {
        AdminMemberResponse lala = new AdminMemberResponse(1L, LALA_NAME, LALA_EMAIL);
        AdminMemberResponse hoho = new AdminMemberResponse(2L, HOHO_NAME, HOHO_EMAIL);
        given(adminCouponService.getCoupons(any()))
                .willReturn(List.of(
                        new AdminCouponResponse(1L, TYPE, "NOT_USED", lala, hoho,
                                LocalDateTime.now(), LocalDateTime.now()),
                        new AdminCouponResponse(2L, TYPE, "RESERVING", hoho, lala,
                                LocalDateTime.now(), LocalDateTime.now()))
                );

        ResultActions resultActions = mockMvc.perform(get("/admin/coupons?"
                        + "status=all"
                        + "&startDate=2022-01-01"
                        + "&endDate=2022-12-01"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/coupons/get-coupons-all",
                Preprocessors.preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].type").type(STRING).description("type"),
                        fieldWithPath("[].status").type(STRING).description("status"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.email").type(STRING).description("senderEmail"),
                        fieldWithPath("[].receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("[].receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("[].receiver.email").type(STRING).description("receiverEmail"),
                        fieldWithPath("[].createdAt").type(STRING).description("createdAt"),
                        fieldWithPath("[].modifiedAt").type(STRING).description("modifiedAt")
                )
        ));
    }

    @DisplayName("기간과 상태에 해당하는 모든 쿠폰을 조회한다.")
    @Test
    void getCouponsByStatus() throws Exception {
        AdminMemberResponse lala = new AdminMemberResponse(1L, LALA_NAME, LALA_EMAIL);
        AdminMemberResponse hoho = new AdminMemberResponse(2L, HOHO_NAME, HOHO_EMAIL);
        given(adminCouponService.getCoupons(any()))
                .willReturn(List.of(
                        new AdminCouponResponse(1L, TYPE, "RESERVING", lala, hoho,
                                LocalDateTime.now(), LocalDateTime.now()),
                        new AdminCouponResponse(2L, TYPE, "RESERVING", hoho, lala,
                                LocalDateTime.now(), LocalDateTime.now()))
                );

        ResultActions resultActions = mockMvc.perform(get("/admin/coupons?"
                        + "status=reserving"
                        + "&startDate=2022-01-01"
                        + "&endDate=2022-12-01"))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("admin/coupons/get-coupons-status",
                Preprocessors.preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].type").type(STRING).description("type"),
                        fieldWithPath("[].status").type(STRING).description("status"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.email").type(STRING).description("senderEmail"),
                        fieldWithPath("[].receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("[].receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("[].receiver.email").type(STRING).description("receiverEmail"),
                        fieldWithPath("[].createdAt").type(STRING).description("createdAt"),
                        fieldWithPath("[].modifiedAt").type(STRING).description("modifiedAt")
                )
        ));
    }
}
