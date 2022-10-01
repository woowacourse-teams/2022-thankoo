package com.woowacourse.thankoo.admin.authentication.presentation;

import com.woowacourse.thankoo.admin.authentication.application.AdminAuthenticationService;
import com.woowacourse.thankoo.admin.authentication.application.dto.AdminLoginRequest;
import com.woowacourse.thankoo.admin.authentication.presentation.dto.AdminLoginResponse;
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
    public ResponseEntity<AdminLoginResponse> signIn(@RequestBody final AdminLoginRequest loginRequest) {
        AdminLoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }
}
