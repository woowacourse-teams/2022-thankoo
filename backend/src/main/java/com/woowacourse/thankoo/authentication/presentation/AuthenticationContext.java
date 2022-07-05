package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.exception.InvalidAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private Long principal;

    public Long getPrincipal() {
        if (principal == null) {
            throw new InvalidAuthenticationException("인증되지 않았습니다.");
        }
        return principal;
    }

    public void setPrincipal(final Long principal) {
        if (this.principal != null) {
            throw new InvalidAuthenticationException("이미 인증 정보가 존재합니다.");
        }
        this.principal = principal;
    }
}
