package com.woowacourse.thankoo.alarm.infrastructure;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.alarm.infrastructure.dto.Attachments;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackMessageRequest;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUserResponse;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUsersResponse;
import com.woowacourse.thankoo.common.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private static final String TOKEN_TYPE = "Bearer ";

    private final String token;
    private final String usersUri;
    private final String messageUri;

    public SlackClient(@Value("${slack.token}") final String token,
                       @Value("${slack.users-uri}") final String usersUri,
                       @Value("${slack.message-uri}") final String messageUri) {
        this.token = token;
        this.usersUri = usersUri;
        this.messageUri = messageUri;
    }

    public SlackUsersResponse getUsers() {
        HttpEntity<SlackUsersResponse> request = new HttpEntity<>(setHeaders(APPLICATION_FORM_URLENCODED_VALUE));
        return REST_TEMPLATE
                .exchange(usersUri, GET, request, SlackUsersResponse.class)
                .getBody();
    }

    public void sendMessage(final String channel, final Attachments attachments) {
        SlackMessageRequest request = SlackMessageRequest.of(channel, attachments);
        HttpHeaders headers = setHeaders(APPLICATION_JSON_VALUE);
        HttpEntity<SlackMessageRequest> requestHttpEntity = new HttpEntity<>(request, headers);
        REST_TEMPLATE.exchange(messageUri, POST, requestHttpEntity, Void.class);
    }

    private HttpHeaders setHeaders(final String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, TOKEN_TYPE + token);
        headers.add(CONTENT_TYPE, contentType);
        return headers;
    }

    public String getUserToken(final String email) {
        SlackUserResponse slackUser = getUser(email, getUsers());
        return slackUser.getUserToken();
    }

    private SlackUserResponse getUser(final String email, final SlackUsersResponse response) {
        return response.getResponses()
                .stream()
                .filter(it -> email.equals(it.getProfile().getEmail()))
                .findFirst()
                .orElseThrow(() -> new InvalidAlarmException(ErrorType.NOT_FOUND_SLACK_USER));
    }
}
