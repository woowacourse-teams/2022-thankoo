package com.woowacourse.thankoo.common.alert.sender;

import com.woowacourse.thankoo.common.alert.AlertSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile("prod")
public class SlackAlertSender implements AlertSender {

    private static final String REQUEST_URI = "https://hooks.slack.com/services";

    @Value("${slack.hook}")
    private String hookUri;

    @Override
    public void send(final String message) {
        WebClient.create(REQUEST_URI)
                .post()
                .uri(hookUri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new MessageRequest(message))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
