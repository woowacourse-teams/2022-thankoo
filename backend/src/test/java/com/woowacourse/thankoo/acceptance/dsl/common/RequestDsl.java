package com.woowacourse.thankoo.acceptance.dsl.common;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public abstract class RequestDsl {

    protected ExtractableResponse<Response> response;

    public void done() {
        if (response == null) {
            throw new RuntimeException("request cannot be done before returning ExtractableResponse");
        }
    }
}
