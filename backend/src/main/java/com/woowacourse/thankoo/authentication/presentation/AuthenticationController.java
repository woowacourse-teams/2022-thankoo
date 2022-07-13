package com.woowacourse.thankoo.authentication.presentation;

import com.woowacourse.thankoo.authentication.application.AuthenticationService;
import com.woowacourse.thankoo.authentication.presentation.dto.GoogleTokenRequest;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/api/sign-in")
    public ResponseEntity<String> signIn() {
        return new RestTemplate().exchange(
                "https://accounts.google.com/o/oauth2/v2/auth?client_id=135992368964-20ad4ul4e3mmia6iok3r9dpg6bshp4uq.apps.googleusercontent.com&redirect_uri=http://localhost:8080/api/sign-in/callback&response_type=code&scope=https://www.googleapis.com/auth/contacts.readonly",
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                String.class);
//        return ResponseEntity.ok(null);
    }

    @GetMapping("/api/sign-in/callback")
    public ResponseEntity<TokenResponse> signInCallback(HttpServletRequest request, @RequestParam String code) {
        System.out.println(request);
        GoogleTokenRequest googleTokenRequest = new GoogleTokenRequest(
                "135992368964-20ad4ul4e3mmia6iok3r9dpg6bshp4uq.apps.googleusercontent.com",
                "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", code);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<GoogleTokenRequest> entity = new HttpEntity<>(googleTokenRequest, httpHeaders);
        String accessToken = new RestTemplate().exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, entity,
                        String.class)
                .getBody();

        System.out.println(accessToken);
        return null;
    }
}
