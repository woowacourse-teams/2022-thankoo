package com.woowacourse.thankoo.admin.common;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminCouponSerialController.class)
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
    protected AdminCouponSerialService adminCouponSerialService;
}
