package com.woowacourse.thankoo.admin.member.application;

import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
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

    public List<AdminMemberResponse> getMembers() {
        List<Member> members = adminMemberRepository.findAll();
        return members.stream()
                .map(AdminMemberResponse::of)
                .collect(Collectors.toList());
    }
}
