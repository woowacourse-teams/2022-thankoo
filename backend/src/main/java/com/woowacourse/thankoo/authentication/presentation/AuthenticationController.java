package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.application.AuthenticationService;
import com.woowacourse.thankoo.authentication.application.dto.SignUpRequest;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/api/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestParam final String code) {
        return ResponseEntity.ok(authenticationService.signIn(code));
    }

    @PostMapping("/api/sign-up")
    public ResponseEntity<TokenResponse> signUp(@RequestHeader(name = HttpHeaders.AUTHORIZATION) final String idToken,
                                                @RequestBody final SignUpRequest signUpRequest) {
        TokenResponse tokenResponse = authenticationService.signUp(idToken, signUpRequest.getName());
        return ResponseEntity.created(URI.create("/api/members/me")).body(tokenResponse);
    }
}
