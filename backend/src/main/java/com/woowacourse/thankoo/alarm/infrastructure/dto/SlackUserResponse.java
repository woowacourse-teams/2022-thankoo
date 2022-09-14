package com.woowacourse.thankoo.alarm.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlackUserResponse {

    @JsonProperty("id")
    private String userToken;
    private Profile profile;

    public SlackUserResponse(final String userToken, final Profile profile) {
        this.userToken = userToken;
        this.profile = profile;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Profile {

        private String email;

        public Profile(final String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Profile{" +
                    "email='" + email + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SlackUserResponse{" +
                "userToken='" + userToken + '\'' +
                ", profile=" + profile +
                '}';
    }
}
