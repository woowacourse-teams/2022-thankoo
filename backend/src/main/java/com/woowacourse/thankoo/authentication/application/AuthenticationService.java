package com.woowacourse.thankoo.authentication.application;

import com.woowacourse.thankoo.authentication.infrastructure.GoogleClient;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.common.util.ProfileImageGenerator;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final GoogleClient googleClient;
    private final ProfileImageGenerator profileImageGenerator;

    public TokenResponse signIn(final String code) {
        String idToken = googleClient.getIdToken(code);
        GoogleProfileResponse profileResponse = googleClient.getProfileResponse(idToken);
        return memberRepository.findBySocialId(profileResponse.getSocialId())
                .map(this::toSignedUpMemberTokenResponse)
                .orElseGet(() -> TokenResponse.ofFirstSignIn(idToken, profileResponse));
    }

    @Transactional
    public TokenResponse signUp(final String idToken, final String name) {
        GoogleProfileResponse profileResponse = googleClient.getProfileResponse(idToken);
        String imageUrl = profileImageGenerator.getRandomImage();
        Member savedMember = memberRepository.save(profileResponse.toEntity(name, imageUrl));
        return toSignedUpMemberTokenResponse(savedMember);
    }

    private TokenResponse toSignedUpMemberTokenResponse(final Member member) {
        return TokenResponse.ofSignedUp(jwtTokenProvider.createToken(String.valueOf(member.getId())), member);
    }
}
