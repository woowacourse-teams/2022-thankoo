package com.woowacourse.thankoo.heart.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.heart.domain.Heart;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HeartResponse {

    private Long heartId;
    private Long senderId;
    private Long receiverId;
    private int count;
    private boolean last;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public HeartResponse(final Long heartId,
                         final Long senderId,
                         final Long receiverId,
                         final int count,
                         final boolean last,
                         final LocalDateTime modifiedAt) {
        this.heartId = heartId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.count = count;
        this.last = last;
        this.modifiedAt = modifiedAt;
    }

    public static HeartResponse from(final Heart heart) {
        return new HeartResponse(heart.getId(),
                heart.getSenderId(),
                heart.getReceiverId(),
                heart.getCount(),
                heart.isLast(),
                heart.getModifiedAt());
    }

    @Override
    public String toString() {
        return "HeartResponse{" +
                "heartId=" + heartId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", count=" + count +
                ", last=" + last +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
