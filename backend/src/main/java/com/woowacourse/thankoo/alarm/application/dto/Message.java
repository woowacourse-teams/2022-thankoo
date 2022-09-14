<<<<<<< HEAD:backend/src/main/java/com/woowacourse/thankoo/alarm/support/Message.java
package com.woowacourse.thankoo.alarm.support;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Message {

    private final String title;
    private final String titleLink;
    private final List<String> emails;
    private final List<String> contents;

    public Message(final String title,
                   final String titleLink,
                   final List<String> emails,
                   final List<String> contents) {
        this.title = title;
        this.titleLink = titleLink;
        this.emails = emails;
        this.contents = contents;
    }

    public static Builder builder() {
        return new Builder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {

        private String title;
        private List<String> email;
        private String titleLink;
        private List<String> contents;

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder titleLink(final String titleLink) {
            this.titleLink = titleLink;
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
            return new Message(title, titleLink, email, contents);
        }
    }
}
=======
package com.woowacourse.thankoo.alarm.application.dto;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Message {

    private final String title;
    private final String titleLink;
    private final List<String> emails;
    private final List<String> contents;

    public Message(final String title,
                   final String titleLink,
                   final List<String> emails,
                   final List<String> contents) {
        this.title = title;
        this.titleLink = titleLink;
        this.emails = emails;
        this.contents = contents;
    }

    public static Message of(final Alarm alarm, final MessageFormStrategy messageFormStrategy) {
        return messageFormStrategy.createFormat(alarm);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(title, message.title) && Objects.equals(titleLink, message.titleLink)
                && Objects.equals(emails, message.emails) && Objects.equals(contents, message.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, titleLink, emails, contents);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {

        private String title;
        private List<String> email;
        private String titleLink;
        private List<String> contents;

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder titleLink(final String titleLink) {
            this.titleLink = titleLink;
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
            return new Message(title, titleLink, email, contents);
        }
    }
}
>>>>>>> daba63a0244b6bacd428d6c3df98da76456f414a:backend/src/main/java/com/woowacourse/thankoo/alarm/application/dto/Message.java
