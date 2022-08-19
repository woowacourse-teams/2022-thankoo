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

    @JsonProperty("sub")
    private String socialId;
    private String email;

    public GoogleProfileResponse(final String socialId, final String email) {
        this.socialId = socialId;
        this.email = email;
    }

    public Member toEntity(final String name, final String imageUrl) {
        return new Member(name, email, socialId, imageUrl);
    }
}
