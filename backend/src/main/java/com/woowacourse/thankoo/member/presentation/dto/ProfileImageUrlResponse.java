package com.woowacourse.thankoo.member.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProfileImageUrlResponse {

    private String imageUrl;

    private ProfileImageUrlResponse(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ProfileImageUrlResponse of(final String imageUrl) {
        return new ProfileImageUrlResponse(imageUrl);
    }

    @Override
    public String toString() {
        return "ProfileImageUrlResponse{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
