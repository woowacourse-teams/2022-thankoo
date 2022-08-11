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
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.thankoo.common.ControllerTest;
import com.woowacourse.thankoo.common.domain.TimeUnit;
import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponTotal;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.MeetingResponse;
import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.ReservationResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponTotalResponse;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
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

        resultActions.andDo(document("coupons/send",
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

        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);
        List<CouponResponse> couponResponses = List.of(
                CouponResponse.of(new MemberCoupon(1L, huni, lala, TYPE, TITLE, MESSAGE, "NOT_USED")),
                CouponResponse.of(new MemberCoupon(2L, huni, lala, TYPE, TITLE, MESSAGE, "RESERVED"))
        );

        given(couponQueryService.getReceivedCoupons(anyLong(), anyString()))
                .willReturn(couponResponses);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/received?status=" + NOT_USED)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponResponses)));

        resultActions.andDo(document("coupons/received-coupons-not-used",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("[].couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("[].sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("[].sender.name").type(STRING).description("senderName"),
                        fieldWithPath("[].sender.email").type(STRING).description("sendEmail"),
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

        given(couponQueryService.getReceivedCoupons(anyLong(), anyString()))
                .willReturn(couponResponses);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/received?status=" + USED)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponResponses)));

        resultActions.andDo(document("coupons/received-coupons-used",
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

    @DisplayName("보낸 쿠폰을 조회한다.")
    @Test
    void getSentCoupons() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);

        List<CouponResponse> couponResponses = List.of(
                CouponResponse.of(new MemberCoupon(1L, huni, lala, TYPE, TITLE, MESSAGE, "USED")),
                CouponResponse.of(new MemberCoupon(2L, huni, lala, TYPE, TITLE, MESSAGE, "EXPIRED"))
        );

        given(couponQueryService.getSentCoupons(anyLong()))
                .willReturn(couponResponses);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/sent")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponResponses)));

        resultActions.andDo(document("coupons/sent-coupons",
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

    @DisplayName("단일 쿠폰과 예약 정보를 조회한다.")
    @Test
    void getCouponWithReservation() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);

        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1L);
        CouponDetailResponse couponDetailResponse = CouponDetailResponse.from(
                new MemberCoupon(1L, huni, lala, CouponType.COFFEE.getValue(), TITLE, MESSAGE,
                        CouponStatus.RESERVING.name()),
                new ReservationResponse(1L, TimeResponse.from(localDateTime, TimeZoneType.ASIA_SEOUL.getId()),
                        ReservationStatus.WAITING.name()));

        given(couponQueryService.getCouponDetail(anyLong(), anyLong()))
                .willReturn(couponDetailResponse);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponDetailResponse)));

        resultActions.andDo(document("coupons/get-coupon-reservation",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("coupon.couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("coupon.sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("coupon.sender.name").type(STRING).description("senderName"),
                        fieldWithPath("coupon.sender.email").type(STRING).description("senderEmail"),
                        fieldWithPath("coupon.sender.imageUrl").type(STRING).description("senderImageUrl"),
                        fieldWithPath("coupon.receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("coupon.receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("coupon.receiver.email").type(STRING).description("receiverEmail"),
                        fieldWithPath("coupon.receiver.imageUrl").type(STRING).description("receiverImageUrl"),
                        fieldWithPath("coupon.content.couponType").type(STRING).description("couponType"),
                        fieldWithPath("coupon.content.title").type(STRING).description("title"),
                        fieldWithPath("coupon.content.message").type(STRING).description("message"),
                        fieldWithPath("coupon.status").type(STRING).description("status"),
                        fieldWithPath("reservation.reservationId").type(NUMBER).description("reservationId"),
                        fieldWithPath("reservation.time.meetingTime").type(STRING).description("date"),
                        fieldWithPath("reservation.time.timeZone").type(STRING).description("timeZone"),
                        fieldWithPath("reservation.status").type(STRING).description("timeZone"),
                        fieldWithPath("meeting").type(NULL).description("meeting")
                )
        ));
    }

    @DisplayName("단일 쿠폰과 미팅을 조회한다.")
    @Test
    void getCouponWithMeeting() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);

        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1L);
        CouponDetailResponse couponDetailResponse = CouponDetailResponse.from(
                new MemberCoupon(1L, huni, lala, CouponType.COFFEE.getValue(), TITLE, MESSAGE,
                        CouponStatus.RESERVING.name()),
                MeetingResponse.of(new Meeting(1L, List.of(huni, lala),
                        new TimeUnit(localDateTime.toLocalDate(), localDateTime, TimeZoneType.ASIA_SEOUL.getId()),
                        MeetingStatus.ON_PROGRESS,
                        new Coupon(huni.getId(), lala.getId(), new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                                CouponStatus.RESERVED))));

        given(couponQueryService.getCouponDetail(anyLong(), anyLong()))
                .willReturn(couponDetailResponse);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponDetailResponse)));

        resultActions.andDo(document("coupons/get-coupon-meeting",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("coupon.couponId").type(NUMBER).description("couponId"),
                        fieldWithPath("coupon.sender.id").type(NUMBER).description("senderId"),
                        fieldWithPath("coupon.sender.name").type(STRING).description("senderName"),
                        fieldWithPath("coupon.sender.email").type(STRING).description("senderEmail"),
                        fieldWithPath("coupon.sender.imageUrl").type(STRING).description("senderImageUrl"),
                        fieldWithPath("coupon.receiver.id").type(NUMBER).description("receiverId"),
                        fieldWithPath("coupon.receiver.name").type(STRING).description("receiverName"),
                        fieldWithPath("coupon.receiver.email").type(STRING).description("receiverEmail"),
                        fieldWithPath("coupon.receiver.imageUrl").type(STRING).description("receiverImageUrl"),
                        fieldWithPath("coupon.content.couponType").type(STRING).description("couponType"),
                        fieldWithPath("coupon.content.title").type(STRING).description("title"),
                        fieldWithPath("coupon.content.message").type(STRING).description("message"),
                        fieldWithPath("coupon.status").type(STRING).description("status"),
                        fieldWithPath("meeting.meetingId").type(NUMBER).description("meetingId"),
                        fieldWithPath("meeting.members.[].id").type(NUMBER).description("memberId"),
                        fieldWithPath("meeting.members.[].name").type(STRING).description("name"),
                        fieldWithPath("meeting.members.[].email").type(STRING).description("email"),
                        fieldWithPath("meeting.members.[].imageUrl").type(STRING).description("imageUrl"),
                        fieldWithPath("meeting.time.meetingTime").type(STRING).description("date"),
                        fieldWithPath("meeting.time.timeZone").type(STRING).description("timeZone"),
                        fieldWithPath("meeting.status").type(STRING).description("timeZone"),
                        fieldWithPath("reservation").type(NULL).description("meeting")
                )
        ));
    }

    @DisplayName("보낸, 받은 쿠폰 개수를 조회한다.")
    @Test
    void getTotalCouponCount() throws Exception {
        given(jwtTokenProvider.getPayload(anyString()))
                .willReturn("1");
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);

        CouponTotalResponse couponTotalResponse = CouponTotalResponse.from(new CouponTotal(10, 12));

        given(couponQueryService.getCouponTotalCount(anyLong()))
                .willReturn(couponTotalResponse);
        ResultActions resultActions = mockMvc.perform(get("/api/coupons/count")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(couponTotalResponse)));

        resultActions.andDo(document("coupons/count",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                responseFields(
                        fieldWithPath("sentCount").type(NUMBER).description("sent count"),
                        fieldWithPath("receivedCount").type(NUMBER).description("received count")
                )
        ));
    }
}
