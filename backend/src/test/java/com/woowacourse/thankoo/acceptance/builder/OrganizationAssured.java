package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.postWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizationAssured {

    private OrganizationAssured() {
    }

    public static OrganizationJoinRequest 조직_번호(final String code) {
        return new OrganizationJoinRequest(code);
    }

    public static OrganizationRequestBuilder request() {
        return new OrganizationRequestBuilder();
    }

    public static class OrganizationRequestBuilder extends RequestBuilder {

        public OrganizationRequestBuilder 내_조직을_조회한다(final TokenResponse tokenResponse) {
            response = getWithToken("/api/organizations/me", tokenResponse.getAccessToken());
            return this;
        }

        public OrganizationRequestBuilder 조직에_참여한다(final TokenResponse tokenResponse,
                                                   final OrganizationJoinRequest request) {
            response = postWithToken("/api/organizations/join", tokenResponse.getAccessToken(), request);
            return this;
        }

        public OrganizationRequestBuilder 조직에_접근한다(final TokenResponse tokenResponse, final Long organizationId) {
            response = putWithToken("/api/organizations/" + organizationId + "/access", tokenResponse.getAccessToken());
            return this;
        }

        public OrganizationResponseBuilder response() {
            return new OrganizationResponseBuilder(response);
        }
    }

    public static class OrganizationResponseBuilder extends ResponseBuilder {

        public OrganizationResponseBuilder(final ExtractableResponse<Response> response) {
            super(response);
        }

        public void 내_조직이_없다() {
            assertThat(bodies(OrganizationResponse.class)).isEmpty();
        }

        public void 조직을_조회한다(final String... codes) {
            List<OrganizationResponse> responses = bodies(OrganizationResponse.class);

            List<String> responseCodes = responses.stream()
                    .map(OrganizationResponse::getOrganizationCode)
                    .collect(Collectors.toList());

            assertAll(
                    () -> assertThat(responses).hasSize(codes.length),
                    () -> assertThat(responseCodes).containsExactly(codes)
            );
        }

        public OrganizationResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }
    }
}
