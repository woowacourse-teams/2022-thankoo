package com.woowacourse.thankoo.member.presentation.dto;

import com.woowacourse.thankoo.organization.domain.MemberModel;
import com.woowacourse.thankoo.organization.domain.OrganizationMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrganizationMemberResponse {

    private Long id;
    private String name;
    private String email;
    private String imageUrl;

    public OrganizationMemberResponse(final Long id, final String name, final String email, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static OrganizationMemberResponse from(final OrganizationMember organizationMember) {
        com.woowacourse.thankoo.member.domain.Member member = organizationMember.getMember();
        return new OrganizationMemberResponse(member.getId(),
                member.getName().getValue(),
                member.getEmail().getValue(),
                member.getImageUrl());
    }

    public static OrganizationMemberResponse from(final MemberModel member) {
        return new OrganizationMemberResponse(member.getId(),
                member.getName(),
                member.getEmail(),
                member.getImageUrl());
    }

    @Override
    public String toString() {
        return "OrganizationMemberResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
