package com.woowacourse.thankoo.acceptance.builder.common;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public abstract class ResponseBuilder {

    protected final ExtractableResponse<Response> response;

    public ResponseBuilder(final ExtractableResponse<Response> response) {
        this.response = response;
    }

    public <T> T body(Class<T> cls) {
        return response.as(cls);
    }

    public <T> List<T> bodies(Class<T> cls) {
        return response.jsonPath().getList("", cls);
    }
}
