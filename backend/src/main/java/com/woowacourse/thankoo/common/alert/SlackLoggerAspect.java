package com.woowacourse.thankoo.common.alert;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
//@Profile("prod")
@Slf4j
public class SlackLoggerAspect {

    @Before("@annotation(com.woowacourse.thankoo.common.alert.SlackLogger)")
    public void log(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length != 1 || !(args[0] instanceof Exception)) {
            log.warn("Slack Logger Failed : Invalid Used");
            return;
        }

        RequestWrapper requestWrapper = extractRequestWrapper();
        ExceptionWrapper exceptionWrapper = extractExceptionWrapper((Exception) args[0]);

        String message = MessageGenerator.generate(requestWrapper, exceptionWrapper);
        System.out.println(message);
    }

    private RequestWrapper extractRequestWrapper() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        Map<String, String> headerMap = extractHeaders(request);

        return new RequestWrapper(requestURI, requestMethod, headerMap);
    }

    private Map<String, String> extractHeaders(final HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private ExceptionWrapper extractExceptionWrapper(final Exception calledException) {
        StackTraceElement[] exceptionStackTrace = calledException.getStackTrace();
        String exceptionClassName = exceptionStackTrace[0].getClassName();
        String exceptionMethodName = exceptionStackTrace[0].getMethodName();
        int exceptionLineNumber = exceptionStackTrace[0].getLineNumber();
        String message = calledException.getMessage();
        return new ExceptionWrapper(exceptionClassName, exceptionMethodName, exceptionLineNumber, message);
    }
}
