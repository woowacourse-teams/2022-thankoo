package com.woowacourse.thankoo.alarm.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttachmentsRequest {

    private static final String MARK_DOWN = "attachments";
    private static final String COLOR = "#FF6347";
    private static final String EMPTY = "";
    private static final String AUTHOR_NAME = "땡쿠 알림봇";
    private static final String ICON_LINK = "https://lh3.googleusercontent.com/a-/AFdZucoRbi9l51n1gvxvdVSg4I-7rRKkNJwpGLTK152Y=s96-c";
    private static final String TITLE = "땡쿠";
    private static final String TITLE_LINK = "https://thankoo.co.kr/";

    @JsonProperty("mrkdwn_in")
    private List<String> markDown;
    private String color;
    private String preText;
    private String text;
    private String authorName;
    private String authorLink;
    private String authorIcon;
    private String title;
    private String titleLink;

    @JsonProperty("fields")
    private List<DetailMessage> detailMessages;

    public AttachmentsRequest(final String preText, final List<DetailMessage> detailMessages) {
        this.markDown = List.of(MARK_DOWN);
        this.color = COLOR;
        this.preText = preText;
        this.authorName = AUTHOR_NAME;
        this.text = EMPTY;
        this.authorLink = ICON_LINK;
        this.authorIcon = ICON_LINK;
        this.title = TITLE;
        this.titleLink = TITLE_LINK;
        this.detailMessages = detailMessages;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DetailMessage {

        private String value;

        public DetailMessage(final String value) {
            this.value = value;
        }
    }
}
