package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.application.AuthenticationService;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
