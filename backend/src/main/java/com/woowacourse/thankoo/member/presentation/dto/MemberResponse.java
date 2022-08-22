package com.woowacourse.thankoo.member.presentation.dto;

import com.woowacourse.thankoo.common.logging.MaskingUtil;
import com.woowacourse.thankoo.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponse {

    private static final int DISPLAY_RANGE = 3;

    private Long id;
    private String name;
    private String email;
    private String imageUrl;

    private MemberResponse(final Long id, final String name, final String email, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.email =  MaskingUtil.mask(email, DISPLAY_RANGE);
        this.imageUrl = imageUrl;
    }

    public static MemberResponse of(final Member member) {
        return new MemberResponse(member.getId(), member.getName().getValue(), member.getEmail().getValue(),
                member.getImageUrl());
    }

    @Override
    public String toString() {
        return "MemberResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
