package com.woowacourse.thankoo.member.application;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createOrGet(final String name) {
        Member member = memberRepository.findByName(name)
                .orElseGet(() -> memberRepository.save(new Member(name)));
        return member.getId();
    }

    public List<MemberResponse> getMembersExcludeMe(final Long memberId) {
        List<Member> members = memberRepository.findAllByOrderByNameAsc();
        return members.stream()
                .filter(member -> !member.isSameId(memberId))
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }
}
