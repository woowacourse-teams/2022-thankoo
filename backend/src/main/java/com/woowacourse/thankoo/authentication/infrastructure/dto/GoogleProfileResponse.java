package com.woowacourse.thankoo.authentication.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.thankoo.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GoogleProfileResponse {

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

    public static Member toEntity(final GoogleProfileResponse googleProfileResponse) {
        String nickname = splitNameFromEmail(googleProfileResponse.email);
        return new Member(nickname, googleProfileResponse.getEmail(), googleProfileResponse.getSocialId(),
                googleProfileResponse.getImageUrl());
    }

    private static String splitNameFromEmail(final String email) {
        return email.split("@")[0];
    }
}
