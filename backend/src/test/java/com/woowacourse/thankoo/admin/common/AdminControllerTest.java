package com.woowacourse.thankoo.admin.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.thankoo.admin.coupon.application.AdminCouponService;
import com.woowacourse.thankoo.admin.coupon.presentation.AdminCouponController;
import com.woowacourse.thankoo.admin.member.application.AdminMemberService;
import com.woowacourse.thankoo.admin.member.presentation.AdminMemberController;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        AdminMemberController.class,
        AdminCouponController.class
})
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class AdminControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected AdminMemberService adminMemberService;

    @MockBean
    protected AdminCouponService adminCouponService;
}
