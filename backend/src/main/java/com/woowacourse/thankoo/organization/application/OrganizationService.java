package com.woowacourse.thankoo.organization.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationMembers;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;

    public void join(final Long memberId, final OrganizationJoinRequest organizationJoinRequest) {
        Organization organization = organizationRepository.findByCodeValue(organizationJoinRequest.getCode())
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
        Member member = getMember(memberId);

        OrganizationMembers organizationMembers = new OrganizationMembers(
                organizationRepository.findOrganizationMembersByMemberOrderByOrderNumber(member));
        organization.join(member, organizationMembers);
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    public void access(final Long memberId, final Long organizationId) {
        Member member = getMember(memberId);
        OrganizationMembers organizationMembers = new OrganizationMembers(
                organizationRepository.findOrganizationMembersByMember(member));

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
        validateOrganization(organizationMembers, organization);

        organizationMembers.switchLastAccessed(organization);
    }

    private void validateOrganization(final OrganizationMembers organizationMembers, final Organization organization) {
        if (!organizationMembers.containsOrganization(organization)) {
            throw new InvalidOrganizationException(ErrorType.NOT_JOINED_ORGANIZATION);
        }
    }
}
