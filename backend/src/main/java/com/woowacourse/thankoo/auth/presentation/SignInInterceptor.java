package com.woowacourse.thankoo.auth.presentation;

import com.woowacourse.thankoo.auth.exception.InvalidTokenException;
import com.woowacourse.thankoo.auth.infrastructure.AuthorizationExtractor;
import com.woowacourse.thankoo.auth.infrastructure.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
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
                .orElseThrow(InvalidTokenException::new);
        authenticationContext.setPrincipal(Long.valueOf(jwtTokenProvider.getPayload(accessToken)));
        return true;
    }
}
