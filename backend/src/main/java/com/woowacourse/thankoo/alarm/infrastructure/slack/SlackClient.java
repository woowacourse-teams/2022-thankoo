package com.woowacourse.thankoo.alarm.infrastructure.slack;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.thankoo.alarm.infrastructure.dto.Attachments;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private final String authorization;
    private final String messageUri;

    public SlackClient(@Value("${slack.authorization}") final String authorization,
                       @Value("${slack.message-uri}") final String messageUri) {
        this.authorization = authorization;
        this.messageUri = messageUri;
    }

    public void sendMessage(final String channel, final Attachments attachments) {
        SlackMessageRequest request = SlackMessageRequest.of(channel, attachments);
        HttpEntity<SlackMessageRequest> requestHttpEntity = new HttpEntity<>(request, setHeaders());
        REST_TEMPLATE.exchange(messageUri, POST, requestHttpEntity, Void.class);
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, authorization);
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return headers;
    }
}
