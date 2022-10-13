package com.woowacourse.thankoo.heart.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.event.Events;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import java.util.Objects;
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
                        name = "UK_HEART_ORGANIZATION_SENDER_RECEIVER",
                        columnNames = {"organization_id", "sender_id", "receiver_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Heart extends BaseEntity {

    private static final int START_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "count")
    private int count;

    @Column(name = "last")
    private boolean last;

    public Heart(final Long id,
                 final Long organizationId,
                 final Long senderId,
                 final Long receiverId,
                 final int count,
                 final boolean last) {
        validateMember(senderId, receiverId);
        validateCount(count);
        this.id = id;
        this.organizationId = organizationId;
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

    private Heart(final Long organizationId,
                  final Long senderId,
                  final Long receiverId,
                  final int count,
                  final boolean last) {
        this(null, organizationId, senderId, receiverId, count, last);
    }

    public static Heart start(final Long organizationId, final Long senderId, final Long receiverId) {
        Heart heart = new Heart(organizationId, senderId, receiverId, START_COUNT, true);
        Events.publish(HeartSentEvent.from(heart));
        return heart;
    }

    public static Heart firstReply(final Long organizationId,
                                   final Long senderId,
                                   final Long receiverId,
                                   final Heart oppositeHeart) {
        Heart heart = new Heart(organizationId, senderId, receiverId, 0, false);
        heart.send(oppositeHeart);
        return heart;
    }

    public void send(final Heart oppositeHeart) {
        validateFinalStatus(oppositeHeart);
        addCount();
        changeStatus();
        oppositeHeart.changeStatus();
        Events.publish(HeartSentEvent.from(this));
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Heart)) {
            return false;
        }
        Heart heart = (Heart) o;
        return Objects.equals(id, heart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Heart{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", count=" + count +
                ", last=" + last +
                '}';
    }
}
