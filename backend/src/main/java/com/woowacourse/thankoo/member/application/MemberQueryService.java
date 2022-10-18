package com.woowacourse.thankoo.member.application;

import com.woowacourse.thankoo.member.domain.MemberQueryRepository;
import com.woowacourse.thankoo.member.presentation.dto.OrganizationMemberResponse;
import com.woowacourse.thankoo.organization.domain.MemberModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public List<OrganizationMemberResponse> getOrganizationMembersExcludeMe(final Long memberId,
                                                                            final Long organizationId) {
        List<MemberModel> members = memberQueryRepository.findOrganizationMembersExcludeMe(organizationId, memberId);
        return members
                .stream()
                .map(OrganizationMemberResponse::from)
                .collect(Collectors.toList());
    }
}
