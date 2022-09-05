package com.woowacourse.thankoo.alarm.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlackUsersResponse {

    @JsonProperty("members")
    private List<SlackUserResponse> responses;

    public SlackUsersResponse(final List<SlackUserResponse> responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "SlackUsersResponse{" +
                "responses=" + responses +
                '}';
    }
}
