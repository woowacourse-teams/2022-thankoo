package com.woowacourse.thankoo.heart.presentation.dto;

import com.woowacourse.thankoo.heart.domain.Heart;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HeartExchangeResponse {

    private HeartResponse sent;
    private HeartResponse received;

    public HeartExchangeResponse(final HeartResponse sent,
                                 final HeartResponse received) {
        this.sent = sent;
        this.received = received;
    }

    public static HeartExchangeResponse of(@Nullable final Heart sentHearts, @Nullable final Heart receivedHearts) {
        return new HeartExchangeResponse(HeartResponse.from(sentHearts), HeartResponse.from(receivedHearts));
    }

    @Override
    public String toString() {
        return "HeartResponses{" +
                "sent=" + sent +
                ", received=" + received +
                '}';
    }
}
