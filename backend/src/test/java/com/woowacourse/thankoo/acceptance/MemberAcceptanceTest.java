package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HUNI;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_LALA;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("MemberAcceptance 는 ")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("본인을 제외한 모든 회원을 조회한다.")
    @Test
    void getMembersExcludeMe() {
        로그인_한다(CODE_LALA);
        로그인_한다(CODE_SKRR);
        TokenResponse tokenResponse = 토큰을_반환한다(로그인_한다(CODE_HUNI));

        ExtractableResponse<Response> response = getWithToken("/api/members", tokenResponse.getAccessToken());
        List<MemberResponse> memberResponses = response.jsonPath().getList(".", MemberResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(memberResponses).hasSize(2),
                () -> assertThat(memberResponses).extracting("name").containsExactly(LALA_NAME, SKRR_NAME)
        );
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMember() {
        TokenResponse tokenResponse = 토큰을_반환한다(로그인_한다(CODE_LALA));

        ExtractableResponse<Response> response = getWithToken("/api/members/me", tokenResponse.getAccessToken());

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(MemberResponse.class)).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(MemberResponse.of(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL)))
        );
    }
}
