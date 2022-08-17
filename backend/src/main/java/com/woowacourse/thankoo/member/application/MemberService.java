package com.woowacourse.thankoo.member.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.util.ProfileImageGenerator;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.application.dto.MemberProfileImageRequest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import com.woowacourse.thankoo.member.presentation.dto.ProfileImageUrlResponse;
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

    @Transactional
    public void updateMemberProfileImage(final Long memberId,
                                         final MemberProfileImageRequest memberProfileImageRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
        member.updateProfileImage(memberProfileImageRequest.getImageName());
    }

    public List<ProfileImageUrlResponse> getProfileImages() {
        return ProfileImageGenerator.getImageUrls()
                .stream()
                .map(ProfileImageUrlResponse::of)
                .collect(Collectors.toList());
    }
}
