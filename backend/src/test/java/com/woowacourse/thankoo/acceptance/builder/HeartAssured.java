package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import com.woowacourse.thankoo.heart.presentation.dto.ReceivedHeartResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class HeartAssured {

    private HeartAssured() {
    }

    public static HeartRequestBuilder request() {
        return new HeartRequestBuilder();
    }

    public static class HeartRequestBuilder extends RequestBuilder {

        public HeartRequestBuilder 마음을_보낸다(final String accessToken, final Long receiverId) {
            response = postWithToken("/api/hearts/send", accessToken, new HeartRequest(receiverId));
            return this;
        }

        public HeartRequestBuilder 응답_가능한_마음을_조회한다(final String accessToken) {
            response = getWithToken("/api/hearts/received", accessToken);
            return this;
        }

        public HeartResponseBuilder response() {
            return new HeartResponseBuilder(response);
        }
    }

    public static class HeartResponseBuilder extends ResponseBuilder {

        public HeartResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public HeartResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }

        public void 조회_성공(final Long... senderIds) {
            List<ReceivedHeartResponse> responses = bodies(ReceivedHeartResponse.class);
            assertAll(
                    () -> assertThat(responses).hasSize(senderIds.length),
                    () -> assertThat(responses).extracting("sender")
                            .extracting("id")
                            .containsExactly(senderIds)
            );
        }
    }
}