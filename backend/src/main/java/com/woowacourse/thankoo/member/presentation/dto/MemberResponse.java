package com.woowacourse.thankoo.member.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponse {

    private Long id;
    private String name;
    private String socialNickname;
    private String imageUrl;

    public MemberResponse(final Long id, final String name, final String socialNickname, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.socialNickname = socialNickname;
        this.imageUrl = imageUrl;
    }
}
