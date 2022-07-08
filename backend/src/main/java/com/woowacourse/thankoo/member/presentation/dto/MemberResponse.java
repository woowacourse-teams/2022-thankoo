package com.woowacourse.thankoo.member.presentation.dto;

import com.woowacourse.thankoo.member.domain.Member;
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

    private MemberResponse(final Long id, final String name, final String socialNickname, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.socialNickname = socialNickname;
        this.imageUrl = imageUrl;
    }

    public static MemberResponse of(final Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getName(), "thankoo.png");
    }
}
