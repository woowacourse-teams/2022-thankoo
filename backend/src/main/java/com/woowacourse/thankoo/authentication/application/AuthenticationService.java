package com.woowacourse.thankoo.authentication.application;

import com.woowacourse.thankoo.authentication.infrastructure.JwtTokenProvider;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public TokenResponse signIn(final String name) {
        Long id = memberService.createOrGet(new GoogleProfileResponse("hoho", "123124124", "url"));
        String token = jwtTokenProvider.createToken(String.valueOf(id));
        return new TokenResponse(token, id);
    }
}
