package com.woowacourse.thankoo.admin.member.application;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.member.application.dto.AdminMemberNameRequest;
import com.woowacourse.thankoo.admin.member.application.dto.AdminMemberSearchRequest;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.member.domain.dto.AdminMemberSearchCondition;
import com.woowacourse.thankoo.admin.member.exception.AdminNotFoundMemberException;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;

    public List<AdminMemberResponse> getMembers(final AdminMemberSearchRequest memberSearchRequest) {
        AdminMemberSearchCondition dateFilterCondition = AdminMemberSearchCondition.of(
                memberSearchRequest.getStartDate(), memberSearchRequest.getEndDate());

        List<Member> members = adminMemberRepository.findAllByCreatedAtBetween(
                dateFilterCondition.getStartDateTime(),
                dateFilterCondition.getEndDateTime()
        );

        return members.stream()
                .map(AdminMemberResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMemberName(final Long memberId, final AdminMemberNameRequest memberNameRequest) {
        Member member = adminMemberRepository.findById(memberId)
                .orElseThrow(() -> new AdminNotFoundMemberException(AdminErrorType.NOT_FOUND_MEMBER));
        member.updateName(memberNameRequest.getName());
    }
}
