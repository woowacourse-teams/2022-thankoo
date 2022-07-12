package com.woowacourse.thankoo.authentication.application;

import com.woowacourse.thankoo.member.domain.Member;
import lombok.Getter;

@Getter
public class GoogleProfileResponse {

    private String email;
    private String socialId;
    private String imageUrl;

    private GoogleProfileResponse() {
    }

    public GoogleProfileResponse(final String email, final String socialId, final String imageUrl) {
        this.email = email;
        this.socialId = socialId;
        this.imageUrl = imageUrl;
    }

    public static Member toEntity(final GoogleProfileResponse googleProfileResponse) {
        return new Member(googleProfileResponse.getEmail(),
                googleProfileResponse.getEmail(),
                googleProfileResponse.getSocialId(),
                googleProfileResponse.getImageUrl());
    }
}
