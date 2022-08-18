package com.woowacourse.thankoo.common.logging;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    private void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    private void putMapping() {
    }

    @Pointcut("execution(* com.woowacourse.thankoo..presentation.*Controller.*(..))")
    private void controllerPointCut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    private void exceptionHandlerCut() {
    }

    @Before("postMapping() || putMapping()")
    public void requestLog(final JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("[ REQUEST ] Controller - {}, Method - {}, Arguments - {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                signature.getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "controllerPointCut() || exceptionHandlerCut()", returning = "response")
    public void responseLog(final JoinPoint joinPoint, ResponseEntity<?> response) {
        Signature signature = joinPoint.getSignature();
        log.info("[ RESPONSE ] Controller - {}, Method - {}, returnBody - {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                signature.getName(),
                response.getBody());
    }
}
