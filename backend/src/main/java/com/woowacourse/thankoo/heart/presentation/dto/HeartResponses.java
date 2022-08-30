package com.woowacourse.thankoo.heart.presentation.dto;

import com.woowacourse.thankoo.heart.domain.Heart;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HeartResponses {

    private List<HeartResponse> sent;
    private List<HeartResponse> received;

    public HeartResponses(final List<HeartResponse> sent,
                          final List<HeartResponse> received) {
        this.sent = sent;
        this.received = received;
    }

    public static HeartResponses of(final List<Heart> sentHearts, final List<Heart> receivedHearts) {
        return new HeartResponses(toHeartResponse(sentHearts), toHeartResponse(receivedHearts));
    }

    private static List<HeartResponse> toHeartResponse(final List<Heart> hearts) {
        return hearts.stream()
                .map(HeartResponse::from)
                .collect(Collectors.toList());
    }
}
