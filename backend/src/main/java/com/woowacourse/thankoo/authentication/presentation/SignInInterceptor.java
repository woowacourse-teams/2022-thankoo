package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.exception.InvalidTokenException;
import com.woowacourse.thankoo.authentication.infrastructure.AuthorizationExtractor;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.common.exception.ErrorType;
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
public class SignInInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String accessToken = AuthorizationExtractor.extract(request)
                .orElseThrow(() -> new InvalidTokenException(ErrorType.INVALID_TOKEN));
        authenticationContext.setPrincipal(Long.valueOf(jwtTokenProvider.getPayload(accessToken)));
        log.debug("[ Auth Request ] USER_ID = {} ", authenticationContext.getPrincipal());
        return true;
    }
}
