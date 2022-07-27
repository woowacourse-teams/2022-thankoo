package com.woowacourse.thankoo.member.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.util.List;
import java.util.Optional;
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
    public Member save(final Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findBySocialId(final String socialId) {
        return memberRepository.findBySocialId(socialId);
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

    @Transactional
    public void updateMemberName(final Long memberId, final MemberNameRequest memberNameRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
        member.updateName(memberNameRequest.getName());
    }
}
