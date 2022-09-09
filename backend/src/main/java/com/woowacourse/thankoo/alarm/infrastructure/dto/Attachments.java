package com.woowacourse.thankoo.alarm.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Attachments {

    private static final String MARK_DOWN = "attachments";
    private static final String DEFAULT_COLOR = "#FF6347";
    private static final String EMPTY = "";
    private static final String AUTHOR_NAME = "땡쿠 알림봇";
    private static final String ICON_LINK = "https://lh3.googleusercontent.com/a-/AFdZucoRbi9l51n1gvxvdVSg4I-7rRKkNJwpGLTK152Y=s96-c";
    private static final String DEFAULT_TITLE = "땡쿠 바로가기";

    @JsonProperty("mrkdwn_in")
    private List<String> markDown;
    private String color;
    private String pretext;
    private String text;

    @JsonProperty("author_link")
    private String authorLink;

    @JsonProperty("author_icon")
    private String authorIcon;
    private String title;

    @JsonProperty("title_link")
    private String titleLink;

    @JsonProperty("fields")
    private List<DetailMessage> detailMessages;

    private Attachments(final List<String> markDown,
                        final String color,
                        final String pretext,
                        final String text,
                        final String authorLink,
                        final String authorIcon,
                        final String title,
                        final String titleLink,
                        final List<DetailMessage> detailMessages) {
        this.markDown = markDown;
        this.color = color;
        this.pretext = pretext;
        this.text = text;
        this.authorLink = authorLink;
        this.authorIcon = authorIcon;
        this.title = title;
        this.titleLink = titleLink;
        this.detailMessages = detailMessages;
    }

    public static Attachments from(final String preText, final String titleLink, final List<String> detailMessages) {
        return new Attachments(
                List.of(MARK_DOWN),
                DEFAULT_COLOR, preText,
                EMPTY,
                ICON_LINK,
                ICON_LINK,
                DEFAULT_TITLE,
                titleLink,
                toDetailMessage(detailMessages));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class DetailMessage {

        private String value;

        public DetailMessage(final String value) {
            this.value = value;
        }
    }

    private static List<DetailMessage> toDetailMessage(final List<String> detailMessages) {
        return detailMessages.stream()
                .map(DetailMessage::new)
                .collect(Collectors.toList());
    }
}
