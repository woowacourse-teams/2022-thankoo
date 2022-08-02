package com.woowacourse.thankoo.acceptance.dsl.common;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public abstract class ResponseDsl {

    protected final ExtractableResponse<Response> response;

    public ResponseDsl(final ExtractableResponse<Response> response) {
        this.response = response;
    }

    public <T> T body(Class<T> cls) {
        return response.as(cls);
    }

    public <T> List<T> bodies(Class<T> cls) {
        return response.jsonPath().getList("", cls);
    }
}
