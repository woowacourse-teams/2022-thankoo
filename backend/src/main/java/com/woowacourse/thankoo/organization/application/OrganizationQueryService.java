package com.woowacourse.thankoo.organization.application;

import com.woowacourse.thankoo.organization.domain.OrganizationQueryRepository;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationQueryService {

    private final OrganizationQueryRepository organizationQueryRepository;

    public List<OrganizationResponse> getMemberOrganizations(final Long memberId) {
        return organizationQueryRepository.findMemberOrganizationsByMemberId(memberId)
                .stream()
                .map(OrganizationResponse::from)
                .collect(Collectors.toList());
    }
}
