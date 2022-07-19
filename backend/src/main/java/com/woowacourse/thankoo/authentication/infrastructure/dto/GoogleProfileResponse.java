package com.woowacourse.thankoo.authentication.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.thankoo.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GoogleProfileResponse {

    public static final String DELIMITER = "@";

    @JsonProperty("id")
    private String socialId;
    private String email;

    @JsonProperty("picture")
    private String imageUrl;

    public GoogleProfileResponse(final String socialId, final String email, final String imageUrl) {
        this.socialId = socialId;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Member toEntity() {
        String nickname = splitNameFromEmail(email);
        return new Member(nickname, email, socialId, imageUrl);
    }

    private static String splitNameFromEmail(final String email) {
        return email.split(DELIMITER)[0];
    }
}
