package com.woowacourse.thankoo.alarm.support;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Message {

    private final String title;
    private final List<String> emails;
    private final List<String> contents;

    public Message(final Builder builder) {
        this.title = builder.title;
        this.emails = builder.email;
        this.contents = builder.contents;
    }

    public static class Builder {

        private String title;
        private List<String> email;
        private List<String> contents;

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder email(final List<String> emails) {
            this.email = emails;
            return this;
        }

        public Builder contents(final List<String> contents) {
            if (this.contents != null) {
                this.contents.addAll(contents);
                return this;
            }
            this.contents = contents;
            return this;
        }

        public Builder content(final String content) {
            if (this.contents == null) {
                this.contents = new ArrayList<>();
            }
            this.contents.add(content);
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
