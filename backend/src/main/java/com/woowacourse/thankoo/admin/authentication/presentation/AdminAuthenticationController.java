package com.woowacourse.thankoo.admin.authentication.presentation;

import com.woowacourse.thankoo.admin.authentication.application.AdminAuthenticationService;
import com.woowacourse.thankoo.admin.authentication.application.dto.AdminSignInRequest;
import com.woowacourse.thankoo.admin.authentication.presentation.dto.AdminSignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminAuthenticationController {

    private final AdminAuthenticationService authenticationService;

    @PostMapping("/admin/sign-in")
    public ResponseEntity<AdminSignInResponse> signIn(@RequestBody final AdminSignInRequest signInRequest) {
        return ResponseEntity.ok().body(authenticationService.signIn(signInRequest));
    }
}
