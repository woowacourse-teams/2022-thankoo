package com.woowacourse.thankoo.heart.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "heart",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_HEART_SENDER_RECEIVER",
                        columnNames = {"sender_id", "receiver_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Heart extends BaseEntity {

    private static final int START_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "count")
    private int count;

    @Column(name = "last")
    private boolean last;

    private Heart(final Long id, final Long senderId, final Long receiverId, final int count, final boolean last) {
        validateMember(senderId, receiverId);
        validateCount(count);
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.count = count;
        this.last = last;
    }

    private void validateMember(final Long senderId, final Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new InvalidHeartException(ErrorType.INVALID_HEART);
        }
    }

    private void validateCount(final int count) {
        if (count < 0) {
            throw new InvalidHeartException(ErrorType.INVALID_HEART);
        }
    }

    private Heart(final Long senderId, final Long receiverId, final int count, final boolean last) {
        this(null, senderId, receiverId, count, last);
    }

    public static Heart start(final Long senderId, final Long receiverId) {
        return new Heart(null, senderId, receiverId, START_COUNT, true);
    }

    public static Heart firstReply(final Long senderId, final Long receiverId, final Heart oppositeHeart) {
        Heart heart = new Heart(null, senderId, receiverId, 0, false);
        heart.send(oppositeHeart);
        return heart;
    }

    public void send(final Heart oppositeHeart) {
        validateFinalStatus(oppositeHeart);
        addCount();
        changeStatus();
        oppositeHeart.changeStatus();
    }

    private void validateFinalStatus(final Heart oppositeHeart) {
        if (last && !oppositeHeart.isLast()) {
            throw new InvalidHeartException(ErrorType.INVALID_HEART);
        }
    }

    private void addCount() {
        count++;
    }

    private void changeStatus() {
        last = !last;
    }
}
