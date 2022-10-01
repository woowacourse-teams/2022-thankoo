package com.woowacourse.thankoo.admin.common;

import antlr.preprocessor.Preprocessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.thankoo.admin.authentication.application.AdminAuthenticationService;
import com.woowacourse.thankoo.admin.authentication.presentation.AdminAuthenticationController;
import com.woowacourse.thankoo.admin.coupon.application.AdminCouponQueryService;
import com.woowacourse.thankoo.admin.coupon.application.AdminCouponService;
import com.woowacourse.thankoo.admin.coupon.presentation.AdminCouponController;
import com.woowacourse.thankoo.admin.member.application.AdminMemberService;
import com.woowacourse.thankoo.admin.member.presentation.AdminMemberController;
import com.woowacourse.thankoo.admin.serial.application.AdminCouponSerialQueryService;
import com.woowacourse.thankoo.admin.serial.application.AdminCouponSerialService;
import com.woowacourse.thankoo.admin.serial.presentation.AdminCouponSerialController;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        AdminMemberController.class,
        AdminCouponController.class,
        AdminCouponSerialController.class,
        AdminAuthenticationController.class
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
    protected AdminCouponQueryService adminCouponQueryService;

    @MockBean
    protected AdminCouponService adminCouponService;

    @MockBean
    protected AdminCouponSerialService adminCouponSerialService;

    @MockBean
    protected AdminCouponSerialQueryService adminCouponSerialQueryService;

    @MockBean
    protected AdminAuthenticationService adminAuthenticationService;

    protected OperationResponsePreprocessor getResponsePreprocessor() {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
    }

    protected OperationRequestPreprocessor getRequestPreprocessor() {
        return Preprocessors.preprocessRequest(Preprocessors.prettyPrint());
    }
}
