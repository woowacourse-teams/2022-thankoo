package com.woowacourse.thankoo.common;


import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.thankoo.authentication.application.AuthenticationService;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationContext;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationController;
import com.woowacourse.thankoo.coupon.application.CouponHistoryService;
import com.woowacourse.thankoo.coupon.presentation.CouponHistoryController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        AuthenticationController.class,
        CouponHistoryController.class
})
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
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
    protected CouponHistoryService couponHistoryService;

    protected OperationResponsePreprocessor getResponsePreprocessor() {
        return Preprocessors.preprocessResponse(prettyPrint());
    }

    protected OperationRequestPreprocessor getRequestPreprocessor() {
        return Preprocessors.preprocessRequest(prettyPrint());
    }
}
