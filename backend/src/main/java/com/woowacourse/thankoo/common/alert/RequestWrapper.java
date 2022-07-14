package com.woowacourse.thankoo.common.alert;

import java.util.Map;
import lombok.Getter;

@Getter
public class RequestWrapper {

    private final String requestURI;
    private final String requestMethod;
    private final Map<String, String> headerMap;

    public RequestWrapper(final String requestURI,
                          final String requestMethod,
                          final Map<String, String> headerMap) {
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
        this.headerMap = headerMap;
    }
}
