package com.woowacourse.thankoo.coupon.presentation;


import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.USED;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
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
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("CouponController 는 ")
public class CouponControllerTest extends ControllerTest {

    @DisplayName("쿠폰을 전송하면 200 OK 를 반환한다.")
    @Test
    void sendCoupon() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        doNothing().when(couponService).saveAll(anyLong(), any(CouponRequest.class));

        ResultActions resultActions = mockMvc.perform(post("/api/coupons/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .content(objectMapper.writeValueAsString(new CouponRequest(List.of(1L),
                                new ContentRequest(TYPE, TITLE, MESSAGE))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andDo(document("coupon/send",
                getRequestPreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                        fieldWithPath("receiverIds").type(ARRAY).description("receiverId"),
                        fieldWithPath("content.couponType").type(STRING).description("couponType"),
                        fieldWithPath("content.title").type(STRING).description("title"),
                        fieldWithPath("content.message").type(STRING).description("message")
                )
        ));
    }

    @DisplayName("사용하지 않은 받은 쿠폰을 조회한다.")
    @Test
    void getReceivedCouponsNotUsed() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");

        Member huni = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);
        List<CouponResponse> couponResponses = List.of(
                CouponResponse.of(new MemberCoupon(1L, huni, lala, TYPE, TITLE, MESSAGE, "NOT_USED")),
                CouponResponse.of(new MemberCoupon(2L, huni, lala, TYPE, TITLE, MESSAGE, "RESERVED"))
        );

        given(couponService.getReceivedCoupons(anyLong(), anyString()))
                .willReturn(couponResponses);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/received?status=" + NOT_USED)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponResponses)));

        resultActions.andDo(document("coupon/received-coupons-not-used",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.socialNickname").type(STRING).description("senderSocialNickname"),
                        fieldWithPath("[].sender.imageUrl").type(STRING).description("senderImageUrl"),
                        fieldWithPath("[].receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("[].receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("[].receiver.socialNickname").type(STRING).description("receiverSocialNickname"),
                        fieldWithPath("[].receiver.imageUrl").type(STRING).description("receiverImageUrl"),
                        fieldWithPath("[].content.couponType").type(STRING).description("couponType"),
                        fieldWithPath("[].content.title").type(STRING).description("title"),
                        fieldWithPath("[].content.message").type(STRING).description("message"),
                        fieldWithPath("[].status").type(STRING).description("status")
                )
        ));
    }

    @DisplayName("사용한 받은 쿠폰을 조회한다.")
    @Test
    void getReceivedCouponsUsed() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);

        List<CouponResponse> couponResponses = List.of(
                CouponResponse.of(new MemberCoupon(1L, huni, lala, TYPE, TITLE, MESSAGE, "USED")),
                CouponResponse.of(new MemberCoupon(2L, huni, lala, TYPE, TITLE, MESSAGE, "EXPIRED"))
        );

        given(couponService.getReceivedCoupons(anyLong(), anyString()))
                .willReturn(couponResponses);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/received?status=" + USED)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponResponses)));

        resultActions.andDo(document("coupon/received-coupons-used",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.email").type(STRING).description("senderEmail"),
                        fieldWithPath("[].sender.imageUrl").type(STRING).description("senderImageUrl"),
                        fieldWithPath("[].receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("[].receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("[].receiver.email").type(STRING).description("receiverEmail"),
                        fieldWithPath("[].receiver.imageUrl").type(STRING).description("receiverImageUrl"),
                        fieldWithPath("[].content.couponType").type(STRING).description("couponType"),
                        fieldWithPath("[].content.title").type(STRING).description("title"),
                        fieldWithPath("[].content.message").type(STRING).description("message"),
                        fieldWithPath("[].status").type(STRING).description("status")
                )
        ));
    }
}
