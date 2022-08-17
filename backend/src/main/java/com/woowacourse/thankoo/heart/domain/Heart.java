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

    @Column(name = "is_final")
    private boolean isFinal;

    private Heart(final Long id, final Long senderId, final Long receiverId, final int count, final boolean isFinal) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.count = count;
        this.isFinal = isFinal;
    }

    private Heart(final Long senderId, final Long receiverId, final int count, final boolean isFinal) {
        this(null, senderId, receiverId, count, isFinal);
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
        if (isFinal && !oppositeHeart.isFinal()) {
            throw new InvalidHeartException(ErrorType.INVALID_HEART);
        }
    }

    private void addCount() {
        count++;
    }

    private void changeStatus() {
        if (isFinal) {
            isFinal = false;
            return;
        }
        isFinal = true;
    }
}
