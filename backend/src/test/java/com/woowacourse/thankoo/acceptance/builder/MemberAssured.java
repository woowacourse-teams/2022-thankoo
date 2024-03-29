package com.woowacourse.thankoo.acceptance.builder;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.get;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.putWithToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.acceptance.builder.common.RequestBuilder;
import com.woowacourse.thankoo.acceptance.builder.common.ResponseBuilder;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.application.dto.MemberProfileImageRequest;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import com.woowacourse.thankoo.member.presentation.dto.OrganizationMemberResponse;
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

        public MemberRequestBuilder 내_정보를_조회한다(final TokenResponse tokenResponse) {
            response = getWithToken("/api/members/me", tokenResponse.getAccessToken());
            return this;
        }

        public MemberRequestBuilder 내_이름_정보를_수정한다(final TokenResponse tokenResponse,
                                                  final MemberNameRequest memberNameRequest) {
            response = putWithToken("/api/members/me/name", tokenResponse.getAccessToken(), memberNameRequest);
            return this;
        }

        public MemberRequestBuilder 내_프로필_이미지_정보를_수정한다(final TokenResponse tokenResponse,
                                                       final MemberProfileImageRequest memberProfileImageRequest) {
            response = putWithToken("/api/members/me/profile-image", tokenResponse.getAccessToken(),
                    memberProfileImageRequest);
            return this;
        }

        public MemberRequestBuilder 프로필_이미지들을_조회한다() {
            response = get("/api/members/profile-images");
            return this;
        }

        public MemberRequestBuilder 나를_제외한_조직의_모든_회원을_조회한다(final TokenResponse tokenResponse,
                                                                 final Long organizationId) {
            response = getWithToken("/api/members?organization=" + organizationId, tokenResponse.getAccessToken());
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

        public MemberResponseBuilder status(final int code) {
            assertThat(response.statusCode()).isEqualTo(code);
            return this;
        }

        public void 내_정보_이다(final MemberResponse memberResponse) {
            assertThat(body(MemberResponse.class)).usingRecursiveComparison()
                    .ignoringFields("id")
                    .ignoringFields("imageUrl")
                    .isEqualTo(memberResponse);
        }

        public void 프로필_이미지가_변경되었다(final String imageUrl) {
            String actualImageUrl = body(MemberResponse.class).getImageUrl();
            assertThat(imageUrl).isEqualTo(actualImageUrl);
        }

        public void 나를_제외하고_모두_조회됨(final int size) {
            List<OrganizationMemberResponse> responses = bodies(OrganizationMemberResponse.class);

            assertThat(responses).hasSize(size);
        }
    }
}
