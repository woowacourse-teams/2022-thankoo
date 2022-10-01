package com.woowacourse.thankoo.admin.authentication.presentation;

import com.woowacourse.thankoo.admin.authentication.exception.InvalidTokenException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.authentication.infrastructure.AuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSignInInterceptor implements HandlerInterceptor {

    private final TokenDecoder accessTokenDecoder;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String accessToken = AuthorizationExtractor.extract(request)
                .orElseThrow(() -> new InvalidTokenException(AdminErrorType.INVALID_TOKEN));
        log.debug("[ Administrator Auth Request ] ADMINISTRATOR_ID = {} ",
                Long.valueOf(accessTokenDecoder.decode(accessToken)));
        return true;
    }
}
