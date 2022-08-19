package com.woowacourse.thankoo.authentication.application.dto;

public class GoogleClientRequest {

    private final String code;
    private final String clientId;
    private final String clientSecret;
    private final String grantType;
    private final String redirectUri;

    public GoogleClientRequest(final String code,
                               final String clientId,
                               final String clientSecret,
                               final String grantType,
                               final String redirectUri) {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.redirectUri = redirectUri;
    }

    public String getCode() {
        return code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
