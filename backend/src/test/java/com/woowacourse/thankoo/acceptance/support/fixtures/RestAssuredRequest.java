package com.woowacourse.thankoo.acceptance.support.fixtures;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class RestAssuredRequest {

    public static ExtractableResponse<Response> get(final String url) {
        return RestAssured
                .given().log().all()
                .when().get(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getWithToken(final String url, final String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postWithToken(final String url, final String token, final Object body) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(url)
                .then().log().all()
                .extract();
    }
}
