package com.woowacourse.thankoo.alarm.infrastructure.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlackUserResponse {

    private String id;
    private Profile profile;

    public SlackUserResponse(final String id, final Profile profile) {
        this.id = id;
        this.profile = profile;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Profile {

        private String email;

        public Profile(final String email) {
            this.email = email;
        }
    }
}
