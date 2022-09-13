package com.woowacourse.thankoo.alarm.infrastructure.slack;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUserResponse;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUsersResponse;
import com.woowacourse.thankoo.common.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackUsersClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private final String authorization;
    private final String usersUri;

    public SlackUsersClient(@Value("${slack.authorization}") final String authorization,
                            @Value("${slack.users-uri}") final String usersUri) {
        this.authorization = authorization;
        this.usersUri = usersUri;
    }

    public SlackUsersResponse getUsers() {
        HttpEntity<SlackUsersResponse> request = new HttpEntity<>(setHeaders());
        return REST_TEMPLATE
                .exchange(usersUri, GET, request, SlackUsersResponse.class)
                .getBody();
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, authorization);
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return headers;
    }

    public String getUserTokenByEmail(final String email) {
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
