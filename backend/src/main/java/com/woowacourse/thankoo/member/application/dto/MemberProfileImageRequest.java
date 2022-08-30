package com.woowacourse.thankoo.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberProfileImageRequest {

    private String imageName;

    public MemberProfileImageRequest(final String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "MemberProfileImageRequest{" +
                "imageUrl='" + imageName + '\'' +
                '}';
    }
}
