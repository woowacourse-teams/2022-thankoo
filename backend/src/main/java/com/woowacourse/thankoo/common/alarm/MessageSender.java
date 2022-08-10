package com.woowacourse.thankoo.common.alarm;

import com.woowacourse.thankoo.common.alarm.dto.SlackMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessageSender {

    public static final String SEND_MESSAGE_URL = "https://slack.com/api/chat.postMessage";

    private final String token;

    public MessageSender(@Value("${slack.token}") final String token) {
        this.token = token;
    }

    public void sendMessage(final String channel, final AlarmMessage message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<SlackMessageRequest> requestHttpEntity = new HttpEntity<>(
                new SlackMessageRequest(channel, message.getValue()), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(SEND_MESSAGE_URL, HttpMethod.POST, requestHttpEntity, Void.class);
    }
}
