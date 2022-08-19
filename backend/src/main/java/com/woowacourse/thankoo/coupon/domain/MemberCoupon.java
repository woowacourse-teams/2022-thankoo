package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MemberCoupon {

    private final Long couponId;
    private final Member sender;
    private final Member receiver;
    private final String couponType;
    private final String title;
    private final String message;
    private final String status;

    public MemberCoupon(final Long couponId,
                        final Member sender,
                        final Member receiver,
                        final String couponType,
                        final String title,
                        final String message,
                        final String status) {
        this.couponId = couponId;
        this.sender = sender;
        this.receiver = receiver;
        this.couponType = couponType;
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public boolean isOwner(final Long memberId) {
        return sender.isSameId(memberId) || receiver.isSameId(memberId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberCoupon)) {
            return false;
        }
        MemberCoupon that = (MemberCoupon) o;
        return Objects.equals(couponId, that.couponId) && Objects.equals(sender, that.sender)
                && Objects.equals(receiver, that.receiver) && Objects.equals(couponType,
                that.couponType) && Objects.equals(title, that.title) && Objects.equals(message,
                that.message) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, sender, receiver, couponType, title, message, status);
    }

    @Override
    public String toString() {
        return "MemberCoupon{" +
                "couponId=" + couponId +
                ", sender=" + sender.getId() +
                ", receiver=" + receiver.getId() +
                ", couponType='" + couponType + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
