package com.woowacourse.thankoo.authentication.presentation.dto;

import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenResponse {

    private boolean joined;
    private String accessToken;
    private Long memberId;
    private String email;

    public TokenResponse(final boolean joined, final String accessToken, final Long memberId, final String email) {
        this.joined = joined;
        this.accessToken = accessToken;
        this.memberId = memberId;
        this.email = email;
    }

    public static TokenResponse ofFirstSignIn(final String idToken, final GoogleProfileResponse profileResponse) {
        return new TokenResponse(false, idToken, null, profileResponse.getEmail());
    }

    public static TokenResponse ofSignedUp(final String accessToken, final Member member) {
        return new TokenResponse(true, accessToken, member.getId(), member.getEmail().getValue());
    }
}
