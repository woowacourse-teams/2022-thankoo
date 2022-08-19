package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.exception.InvalidAuthenticationException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private Long principal;

    public Long getPrincipal() {
        if (principal == null) {
            throw new InvalidAuthenticationException(ErrorType.NOT_AUTHENTICATED);
        }
        return principal;
    }

    public void setPrincipal(final Long principal) {
        if (this.principal != null) {
            throw new InvalidAuthenticationException(ErrorType.ALREADY_AUTHENTICATED);
        }
        this.principal = principal;
    }
}
