package com.woowacourse.thankoo.authentication.application;

import com.woowacourse.thankoo.authentication.infrastructure.GoogleClient;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.MemberService;
import com.woowacourse.thankoo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final GoogleClient googleClient;

    public TokenResponse signIn(final String code) {
        String idToken = googleClient.getIdToken(code);
        GoogleProfileResponse profileResponse = googleClient.getProfileResponse(idToken);
        return memberService.findBySocialId(profileResponse.getSocialId())
                .map(this::toSignedUpMemberTokenResponse)
                .orElseGet(() -> TokenResponse.ofFirstSignIn(idToken, profileResponse));
    }

    public TokenResponse signUp(final String idToken, final String name) {
        GoogleProfileResponse profileResponse = googleClient.getProfileResponse(idToken);
        Member savedMember = memberService.save(profileResponse.toEntity(name));
        return toSignedUpMemberTokenResponse(savedMember);
    }

    private TokenResponse toSignedUpMemberTokenResponse(final Member member) {
        return TokenResponse.ofSignedUp(jwtTokenProvider.createToken(String.valueOf(member.getId())), member);
    }
}
