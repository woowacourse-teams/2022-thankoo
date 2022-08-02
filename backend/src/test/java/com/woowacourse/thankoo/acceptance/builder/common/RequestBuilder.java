package com.woowacourse.thankoo.acceptance.builder.common;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public abstract class RequestBuilder {

    protected ExtractableResponse<Response> response;

    public void done() {
        if (response == null) {
            throw new RuntimeException("request cannot be done before returning ExtractableResponse");
        }
    }
}
