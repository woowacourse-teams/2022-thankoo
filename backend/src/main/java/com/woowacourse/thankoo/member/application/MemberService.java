package com.woowacourse.thankoo.member.application;

import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
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
    public Long createOrGet(final GoogleProfileResponse googleProfileResponse) {
        Member member = memberRepository.findBySocialId(googleProfileResponse.getSocialId())
                .orElseGet(() -> memberRepository.save(googleProfileResponse.toEntity()));
        return member.getId();
    }

    public List<MemberResponse> getMembersExcludeMe(final Long memberId) {
        List<Member> members = memberRepository.findAllByIdNotOrderByNameAsc(memberId);
        return members.stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }

    public MemberResponse getMember(final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
        return MemberResponse.of(member);
    }
}
