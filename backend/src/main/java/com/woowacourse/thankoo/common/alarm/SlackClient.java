package com.woowacourse.thankoo.common.alarm;

import com.woowacourse.thankoo.common.alarm.dto.SlackMessageRequest;
import com.woowacourse.thankoo.common.alarm.dto.SlackUserResponse;
import com.woowacourse.thankoo.common.alarm.dto.SlackUsersResponse;
import com.woowacourse.thankoo.common.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackClient {

    public static final String USERS_URL = "https://slack.com/api/users.list";
    public static final String SEND_MESSAGE_URL = "https://slack.com/api/chat.postMessage";

    private final String token;

    public SlackClient(@Value("${slack.token}") final String token) {
        this.token = token;
    }

    public void sendAlarm(final String email, final String message) {
        SlackUserResponse response = findUser(email, getSlackUsers());
        sendMessage(response.getId(), message);
    }

    public SlackUsersResponse getSlackUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<SlackUsersResponse> request = new HttpEntity<>(headers);
        return new RestTemplate()
                .exchange(USERS_URL, HttpMethod.GET, request, SlackUsersResponse.class)
                .getBody();
    }

    public SlackUserResponse findUser(final String email, final SlackUsersResponse response) {
        return response.getResponses()
                .stream()
                .filter(userResponse -> equalsEmail(email, userResponse))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(ErrorType.NOT_FOUND_SLACK_USER.getMessage()));
    }

    private boolean equalsEmail(final String email, final SlackUserResponse userResponse) {
        String findEmail = userResponse.getProfile().getEmail();
        if (findEmail != null) {
            return findEmail.equals(email);
        }
        return false;
    }

    public void sendMessage(final String channel, final String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<SlackMessageRequest> requestHttpEntity = new HttpEntity<>(
                new SlackMessageRequest(channel, message), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(SEND_MESSAGE_URL, HttpMethod.POST, requestHttpEntity, Void.class);
    }
}
