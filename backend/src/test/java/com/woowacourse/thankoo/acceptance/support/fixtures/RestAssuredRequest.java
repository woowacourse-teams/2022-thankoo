package com.woowacourse.thankoo.acceptance.support.fixtures;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RestAssuredRequest {

    public static ExtractableResponse<Response> get(final String url) {
        return RestAssured
                .given().log().all()
                .when().get(url)
                .then().log().all()
                .extract();
    }
}
