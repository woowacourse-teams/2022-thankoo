package com.woowacourse.thankoo.acceptance.dsl;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.acceptance.dsl.common.RequestDsl;
import com.woowacourse.thankoo.acceptance.dsl.common.ResponseDsl;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class MemberAssured {

    private final ExtractableResponse<Response> response;

    public MemberAssured(final ExtractableResponse<Response> response) {
        this.response = response;
    }

    public static MemberRequestDsl request() {
        return new MemberRequestDsl();
    }

    public static class MemberRequestDsl extends RequestDsl {

        public MemberRequestDsl 나를_제외한_모든_회원을_조회한다(final TokenResponse tokenResponse) {
            response = getWithToken("/api/members", tokenResponse.getAccessToken());
            return this;
        }

        public MemberRequestDsl 내_정보를_조회한다(final TokenResponse tokenResponse) {
            response = getWithToken("/api/members/me", tokenResponse.getAccessToken());
            return this;
        }

        public MemberRequestDsl 내_정보를_수정한다(final TokenResponse tokenResponse, final MemberNameRequest memberNameRequest) {
            response = putWithToken("/api/members/me", tokenResponse.getAccessToken(), memberNameRequest);
            return this;
        }

        public MemberResponseDsl response() {
            return new MemberResponseDsl(response);
        }
    }

    public static class MemberResponseDsl extends ResponseDsl {

        public MemberResponseDsl(final ExtractableResponse<Response> response) {
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

        public MemberResponseDsl status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }
    }

}
