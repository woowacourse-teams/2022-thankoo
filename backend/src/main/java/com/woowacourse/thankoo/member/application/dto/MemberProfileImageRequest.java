package com.woowacourse.thankoo.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberProfileImageRequest {

    private String imageUrl;

    public MemberProfileImageRequest(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "MemberProfileImageRequest{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
