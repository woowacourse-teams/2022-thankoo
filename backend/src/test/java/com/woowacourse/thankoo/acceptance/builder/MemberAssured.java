package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class MemberAssured {

    private MemberAssured() {

    }

    public static MemberRequestBuilder request() {
        return new MemberRequestBuilder();
    }

    public static class MemberRequestBuilder extends RequestBuilder {

        public MemberRequestBuilder 나를_제외한_모든_회원을_조회한다(final TokenResponse tokenResponse) {
            response = getWithToken("/api/members", tokenResponse.getAccessToken());
            return this;
        }

        public MemberRequestBuilder 내_정보를_조회한다(final TokenResponse tokenResponse) {
            response = getWithToken("/api/members/me", tokenResponse.getAccessToken());
            return this;
        }

        public MemberRequestBuilder 내_정보를_수정한다(final TokenResponse tokenResponse,
                                               final MemberNameRequest memberNameRequest) {
            response = putWithToken("/api/members/me", tokenResponse.getAccessToken(), memberNameRequest);
            return this;
        }

        public MemberResponseBuilder response() {
            return new MemberResponseBuilder(response);
        }
    }

    public static class MemberResponseBuilder extends ResponseBuilder {

        public MemberResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public void 조회_성공한다(final String... names) {
            if (names.length == 0) {
                throw new RuntimeException("name should exist");
            }
            List<MemberResponse> memberResponses = bodies(MemberResponse.class);
            assertAll(
                    () -> assertThat(memberResponses).hasSize(names.length),
                    () -> assertThat(memberResponses).extracting("name").containsExactly(names)
            );
        }

        public void 내_정보_이다(final MemberResponse memberResponse) {
            assertThat(response.as(MemberResponse.class)).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(memberResponse);
        }

        public MemberResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }
    }
}
