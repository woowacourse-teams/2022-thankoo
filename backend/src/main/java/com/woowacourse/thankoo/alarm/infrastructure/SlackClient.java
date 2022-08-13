package com.woowacourse.thankoo.alarm.infrastructure;

import com.woowacourse.thankoo.alarm.AlarmMessage;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackMessageRequest;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUserResponse;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUsersResponse;
import com.woowacourse.thankoo.common.exception.ErrorType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class SlackClient {

    private static final String TOKEN_TYPE = "Bearer ";

    private final String token;
    private final String usersUri;
    private final String messageUri;

    public SlackClient(final String token, final String usersUri, final String messageUri) {
        this.token = token;
        this.usersUri = usersUri;
        this.messageUri = messageUri;
    }

    public SlackUsersResponse getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_TYPE + token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<SlackUsersResponse> request = new HttpEntity<>(headers);
        return new RestTemplate()
                .exchange(usersUri, HttpMethod.GET, request, SlackUsersResponse.class)
                .getBody();
    }

    public SlackUserResponse getUser(final String email, final SlackUsersResponse response) {
        return response.getResponses()
                .stream()
                .filter(userResponse -> email.equals(userResponse.getProfile().getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(ErrorType.NOT_FOUND_SLACK_USER.getMessage()));
    }

    public String getUserToken(final String email) {
        SlackUsersResponse slackUsers = getUsers();
        SlackUserResponse slackUser = getUser(email, slackUsers);
        return slackUser.getUserToken();
    }

    public void sendMessage(final String channel, final AlarmMessage message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_TYPE + token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<SlackMessageRequest> requestHttpEntity = new HttpEntity<>(
                new SlackMessageRequest(channel, message.getValue()), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(messageUri, HttpMethod.POST, requestHttpEntity, Void.class);
    }
}
