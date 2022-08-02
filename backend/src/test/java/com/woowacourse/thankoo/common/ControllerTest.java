package com.woowacourse.thankoo.common;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.thankoo.authentication.application.AuthenticationService;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationContext;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationController;
import com.woowacourse.thankoo.coupon.application.CouponQueryService;
import com.woowacourse.thankoo.coupon.application.CouponService;
import com.woowacourse.thankoo.coupon.presentation.CouponController;
import com.woowacourse.thankoo.meeting.application.MeetingQueryService;
import com.woowacourse.thankoo.meeting.application.MeetingService;
import com.woowacourse.thankoo.meeting.presentation.MeetingController;
import com.woowacourse.thankoo.member.application.MemberService;
import com.woowacourse.thankoo.member.presentation.MemberController;
import com.woowacourse.thankoo.reservation.application.ReservationQueryService;
import com.woowacourse.thankoo.reservation.application.ReservationService;
import com.woowacourse.thankoo.reservation.presentation.ReservationController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        AuthenticationController.class,
        CouponController.class,
        MemberController.class,
        ReservationController.class,
        MeetingController.class
})
@Import(MockMvcConfig.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthenticationService authenticationService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected CouponService couponService;

    @MockBean
    protected CouponQueryService couponQueryService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected ReservationService reservationService;

    @MockBean
    protected ReservationQueryService reservationQueryService;

    @MockBean
    protected MeetingQueryService meetingQueryService;

    @MockBean
    protected MeetingService meetingService;

    protected OperationResponsePreprocessor getResponsePreprocessor() {
        return Preprocessors.preprocessResponse(prettyPrint());
    }

    protected OperationRequestPreprocessor getRequestPreprocessor() {
        return Preprocessors.preprocessRequest(prettyPrint());
    }
}
