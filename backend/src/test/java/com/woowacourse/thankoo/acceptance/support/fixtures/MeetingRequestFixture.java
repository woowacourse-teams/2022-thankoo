package com.woowacourse.thankoo.acceptance.support.fixtures;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MeetingRequestFixture {

    public static ExtractableResponse<Response> 미팅을_조회한다(final String token) {
        return getWithToken("/api/meetings", token);
    }
}
