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

    public static final String AUTHORIZATION_TYPE = "Bearer ";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private final String clientId;
    private final String clientSecret;
    private final String grantType;
    private final String redirectUri;
    private final String tokenRequestUrl;
    private final String userInfoRequestUrl;

    public GoogleClient(@Value("${oauth.google.client-id}") final String clientId,
                        @Value("${oauth.google.client-secret}") final String clientSecret,
                        @Value("${oauth.google.grant-type}") final String grantType,
                        @Value("${oauth.google.redirect-uri}") final String redirectUri,
                        @Value("${oauth.google.token-url}") final String tokenRequestUrl,
                        @Value("${oauth.google.profile-url}") final String userInfoRequestUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.redirectUri = redirectUri;
        this.tokenRequestUrl = tokenRequestUrl;
        this.userInfoRequestUrl = userInfoRequestUrl;
    }

    public String getAccessToken(final String code) {
        HttpHeaders headers = getUrlEncodedHeader();
        MultiValueMap<String, String> parameters = getGoogleRequestParameters(code);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
        GoogleTokenResponse tokenResponse = requestGoogleToken(httpEntity);
        return Objects.requireNonNull(tokenResponse).getAccessToken();
    }

    private HttpHeaders getUrlEncodedHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
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
                .exchange(tokenRequestUrl,
                        HttpMethod.POST,
                        entity,
                        GoogleTokenResponse.class)
                .getBody();
    }

    public GoogleProfileResponse getProfile(final String accessToken) {
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.add(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TYPE + accessToken);
        return requestProfile(new HttpEntity<>(userInfoHeaders));
    }

    private GoogleProfileResponse requestProfile(final HttpEntity<GoogleProfileResponse> httpEntity) {
        return REST_TEMPLATE
                .exchange(userInfoRequestUrl,
                        HttpMethod.GET,
                        httpEntity,
                        GoogleProfileResponse.class)
                .getBody();
    }
}
