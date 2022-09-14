<<<<<<< HEAD
package com.woowacourse.thankoo.heart.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.heart.domain.MemberHeart;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReceivedHeartResponse {

    private Long heartId;
    private MemberResponse sender;
    private int count;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receivedAt;
    private boolean last;

    private ReceivedHeartResponse(final Long heartId,
                                  final MemberResponse sender,
                                  final int count,
                                  final LocalDateTime receivedAt,
                                  final Boolean last) {
        this.heartId = heartId;
        this.sender = sender;
        this.count = count;
        this.receivedAt =receivedAt;
        this.last = last;
    }

    public static ReceivedHeartResponse from(final MemberHeart memberHeart) {
        return new ReceivedHeartResponse(memberHeart.getHeartId(),
                MemberResponse.of(memberHeart.getSender()),
                memberHeart.getCount(),
                memberHeart.getModifiedAt(),
                memberHeart.isLast());
    }
}
=======
package com.woowacourse.thankoo.heart.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.heart.domain.MemberHeart;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReceivedHeartResponse {

    private Long heartId;
    private MemberResponse sender;
    private int count;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receivedAt;
    private boolean last;

    private ReceivedHeartResponse(final Long heartId,
                                  final MemberResponse sender,
                                  final int count,
                                  final LocalDateTime receivedAt,
                                  final Boolean last) {
        this.heartId = heartId;
        this.sender = sender;
        this.count = count;
        this.receivedAt = receivedAt;
        this.last = last;
    }

    public static ReceivedHeartResponse from(final MemberHeart memberHeart) {
        return new ReceivedHeartResponse(memberHeart.getHeartId(),
                MemberResponse.of(memberHeart.getSender()),
                memberHeart.getCount(),
                memberHeart.getModifiedAt(),
                memberHeart.isLast());
    }

    @Override
    public String toString() {
        return "ReceivedHeartResponse{" +
                "heartId=" + heartId +
                ", sender=" + sender +
                ", count=" + count +
                ", receivedAt=" + receivedAt +
                ", last=" + last +
                '}';
    }
}
>>>>>>> daba63a0244b6bacd428d6c3df98da76456f414a
