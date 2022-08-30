package com.woowacourse.thankoo.authentication.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.thankoo.authentication.exception.GoogleClientException;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleTokenResponse;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
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

    private static final String AUTHORIZATION_TYPE = "Bearer ";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String JWT_DELIMITER = "\\.";
    private static final int PAYLOAD = 1;

    private final String clientId;
    private final String clientSecret;
    private final String grantType;
    private final String redirectUri;
    private final String tokenRequestUrl;

    public GoogleClient(@Value("${oauth.google.client-id}") final String clientId,
                        @Value("${oauth.google.client-secret}") final String clientSecret,
                        @Value("${oauth.google.grant-type}") final String grantType,
                        @Value("${oauth.google.redirect-uri}") final String redirectUri,
                        @Value("${oauth.google.token-url}") final String tokenRequestUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.redirectUri = redirectUri;
        this.tokenRequestUrl = tokenRequestUrl;
    }

    public String getIdToken(final String code) {
        HttpHeaders headers = getUrlEncodedHeader();
        MultiValueMap<String, String> parameters = getGoogleRequestParameters(code);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
        GoogleTokenResponse tokenResponse = requestGoogleToken(httpEntity);
        return tokenResponse.getIdToken();
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

    public GoogleProfileResponse getProfileResponse(final String idToken) {
        return toGoogleProfileResponse(idToken);
    }

    private GoogleProfileResponse toGoogleProfileResponse(final String idToken) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> profile = objectMapper.readValue(getProfileFromToken(idToken), Map.class);
            String socialId = profile.get("sub");
            String email = profile.get("email");

            return new GoogleProfileResponse(socialId, email);
        } catch (JsonProcessingException e) {
            throw new GoogleClientException(e);
        }
    }

    private String getProfileFromToken(final String token) {
        return decode(getPayload(token));
    }

    private String getPayload(final String token) {
        return token.split(JWT_DELIMITER)[PAYLOAD];
    }

    private String decode(final String payload) {
        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }
}
