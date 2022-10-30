package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.application.dto.GoogleClientRequest;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleTokenResponse;
import com.woowacourse.thankoo.common.fixtures.OAuthFixture;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MockOAuthController {

    @PostMapping(path = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GoogleTokenResponse> getAccessToken(GoogleClientRequest googleClientRequest) {
        String idToken = OAuthFixture.CACHED_OAUTH_PROFILE.get(googleClientRequest.getCode());
        return ResponseEntity.ok().body(new GoogleTokenResponse(idToken));
    }
}
