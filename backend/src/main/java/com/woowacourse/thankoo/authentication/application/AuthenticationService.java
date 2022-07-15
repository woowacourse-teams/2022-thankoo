package com.woowacourse.thankoo.authentication.application;

import com.woowacourse.thankoo.authentication.infrastructure.GoogleClient;
import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final GoogleClient googleClient;

    public TokenResponse signIn(final String code) {
        String accessToken = googleClient.getAccessToken(code);
        GoogleProfileResponse profileResponse = googleClient.getProfile(accessToken);
        Long id = memberService.createOrGet(profileResponse);
        String token = jwtTokenProvider.createToken(String.valueOf(id));
        return new TokenResponse(token, id);
    }
}
