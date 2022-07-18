package com.woowacourse.thankoo.common.alert;

import static com.woowacourse.thankoo.common.alert.ExceptionWrapper.extractExceptionWrapper;

import com.woowacourse.thankoo.authentication.exception.InvalidAuthenticationException;
import com.woowacourse.thankoo.authentication.presentation.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("prod")
@RequiredArgsConstructor
@Slf4j
public class SlackLoggerAspect {

    private final AuthenticationContext authenticationContext;
    private final AlertSender alertSender;

    @Before("@annotation(com.woowacourse.thankoo.common.alert.SlackLogger)")
    public void sendLogForError(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length != 1 || !(args[0] instanceof Exception)) {
            log.warn("Slack Logger Failed : Invalid Used");
            return;
        }
        ExceptionWrapper exceptionWrapper = extractExceptionWrapper((Exception) args[0]);
        alertSender.send(MessageGenerator.generate(extractMember(), exceptionWrapper));
    }

    private String extractMember() {
        try {
            return String.valueOf(authenticationContext.getPrincipal());
        } catch (InvalidAuthenticationException e) {
            return "NO AUTH";
        }
    }
}
