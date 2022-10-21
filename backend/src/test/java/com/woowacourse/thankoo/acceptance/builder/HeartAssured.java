package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.heart.presentation.dto.HeartExchangeResponse;
import com.woowacourse.thankoo.heart.presentation.dto.HeartRequest;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class HeartAssured {

    private HeartAssured() {
    }

    public static HeartRequestBuilder request() {
        return new HeartRequestBuilder();
    }

    public static class HeartRequestBuilder extends RequestBuilder {

        public HeartRequestBuilder 마음을_보낸다(final Long organizationId, final String accessToken, final Long receiverId) {
            response = postWithToken("/api/hearts/send", accessToken, new HeartRequest(organizationId, receiverId));
            return this;
        }

        public HeartRequestBuilder 응답_가능한_마음을_조회한다(final Long organizationId, final String accessToken) {
            response = getWithToken("/api/hearts/me?organization=" + organizationId, accessToken);
            return this;
        }

        public HeartRequestBuilder 교환한_마음을_조회한다(final Long organizationId, final Long receiverId,
                                                final String accessToken) {
            response = getWithToken("/api/hearts?organization=" + organizationId + "&receiver=" + receiverId,
                    accessToken);
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

        public void 조회_성공(final int sentSize, final int receivedSize) {
            HeartResponses response = body(HeartResponses.class);
            assertAll(
                    () -> assertThat(response.getSent()).hasSize(sentSize),
                    () -> assertThat(response.getReceived()).hasSize(receivedSize)
            );
        }

        public void 조회에_성공한다(final Long senderId, final Long receiverId) {
            HeartExchangeResponse response = body(HeartExchangeResponse.class);

            assertAll(
                    () -> assertThat(response.getSent().getSenderId()).isEqualTo(senderId),
                    () -> assertThat(response.getSent().getReceiverId()).isEqualTo(receiverId),
                    () -> assertThat(response.getReceived().getSenderId()).isEqualTo(receiverId),
                    () -> assertThat(response.getReceived().getReceiverId()).isEqualTo(senderId)
            );
        }

        public void 받은_기록이_없으면_null_이다() {
            HeartExchangeResponse response = body(HeartExchangeResponse.class);

            assertAll(
                    () -> assertThat(response.getSent()).isNotNull(),
                    () -> assertThat(response.getReceived()).isNull()
            );
        }
    }
}
