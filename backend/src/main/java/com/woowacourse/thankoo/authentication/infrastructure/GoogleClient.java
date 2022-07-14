package com.woowacourse.thankoo.authentication.infrastructure;

import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleTokenResponse;
import java.util.Objects;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
public class GoogleClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private static final String USER_INFO_REQUEST_URL = "https://www.googleapis.com/userinfo/v2/me";

    private final String clientId;
    private final String clientSecret;
    private final String grantType;
    private final String redirectUri;

    public GoogleClient(@Value("${oauth.google.client-id}") final String clientId,
                        @Value("${oauth.google.client-secret}") final String clientSecret,
                        @Value("${oauth.google.grant-type}") final String grantType,
                        @Value("${oauth.google.redirect-uri}") final String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.redirectUri = redirectUri;
    }

    public String getAccessToken(final String code) {
        HttpHeaders headers = getUrlEncodedHeader();
        MultiValueMap<String, String> parameters = getGoogleRequestParameters(code);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
        GoogleTokenResponse tokenResponse = requestGoogleToken(httpEntity);
        return Objects.requireNonNull(tokenResponse).getAccessToken();
    }

    private MultiValueMap<String, String> getGoogleRequestParameters(final String code) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("client_id", clientId);
        parameters.add("client_secret", clientSecret);
        parameters.add("grant_type", grantType);
        parameters.add("redirect_uri", redirectUri);
        return parameters;
    }

    private GoogleTokenResponse requestGoogleToken(final HttpEntity<MultiValueMap<String, String>> entity) {
        return REST_TEMPLATE
                .exchange(TOKEN_REQUEST_URL,
                        HttpMethod.POST,
                        entity,
                        GoogleTokenResponse.class)
                .getBody();
    }

    private HttpHeaders getUrlEncodedHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public GoogleProfileResponse getProfile(final String accessToken) {
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        return requestProfile(new HttpEntity<>(userInfoHeaders));
    }

    private GoogleProfileResponse requestProfile(final HttpEntity<GoogleProfileResponse> httpEntity) {
        return REST_TEMPLATE
                .exchange(USER_INFO_REQUEST_URL,
                        HttpMethod.GET,
                        new HttpEntity<>(httpEntity),
                        GoogleProfileResponse.class)
                .getBody();
    }
}
