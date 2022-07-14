package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.application.dto.GoogleClientRequest;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleTokenResponse;
import com.woowacourse.thankoo.common.fixtures.OAuthFixture;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockOAuthController {

    @PostMapping(path = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GoogleTokenResponse> getAccessToken(GoogleClientRequest googleClientRequest) {
        String accessToken = OAuthFixture.CACHED_OAUTH_TOKEN.get(googleClientRequest.getCode());
        return ResponseEntity.ok().body(new GoogleTokenResponse(accessToken));
    }

    @GetMapping("/userinfo/me")
    public ResponseEntity<GoogleProfileResponse> getProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String accessToken = authorization.split(" ")[1];
        return ResponseEntity.ok().body(OAuthFixture.CACHED_OAUTH_PROFILE.get(accessToken));
    }
}
