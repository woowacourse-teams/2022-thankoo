package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.meeting.presentation.dto.SimpleMeetingResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class MeetingAssured {

    private MeetingAssured() {
    }

    public static MeetingRequestBuilder request() {
        return new MeetingRequestBuilder();
    }

    public static class MeetingRequestBuilder extends RequestBuilder {

        public MeetingRequestBuilder 미팅을_조회한다(final String token, final Long organizationId) {
            response = getWithToken("/api/organizations/" + organizationId + "/meetings", token);
            return this;
        }

        public MeetingRequestBuilder 미팅을_완료한다(final String token) {
            if (response == null) {
                throw new RuntimeException("get meeting first");
            }

            List<SimpleMeetingResponse> responses = response.jsonPath().getList("", SimpleMeetingResponse.class);
            response = putWithToken("/api/meetings/" + responses.get(0).getMeetingId(), token);
            return this;
        }

        public MeetingResponseBuilder response() {
            return new MeetingResponseBuilder(response);
        }
    }

    public static class MeetingResponseBuilder extends ResponseBuilder {

        public MeetingResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public MeetingResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }

        public void 미팅이_조회됨(final int size) {
            List<SimpleMeetingResponse> simpleMeetingResponses = bodies(SimpleMeetingResponse.class);
            assertThat(simpleMeetingResponses).hasSize(size);
        }
    }
}
