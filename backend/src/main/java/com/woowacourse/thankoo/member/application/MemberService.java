package com.woowacourse.thankoo.member.application;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long createOrGet(final String name) {
        Member member = memberRepository.findByName(name)
                .orElseGet(() -> memberRepository.save(new Member(name)));
        return member.getId();
    }
}
