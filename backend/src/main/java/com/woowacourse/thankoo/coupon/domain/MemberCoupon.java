package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MemberCoupon {

    private final Long couponId;
    private final Long organizationId;
    private final Member sender;
    private final Member receiver;
    private final String couponType;
    private final String title;
    private final String message;
    private final String status;
    private final LocalDate createdDate;

    public MemberCoupon(final Long couponId,
                        final Long organizationId,
                        final Member sender,
                        final Member receiver,
                        final String couponType,
                        final String title,
                        final String message,
                        final String status,
                        final LocalDate createdDate) {
        this.couponId = couponId;
        this.organizationId = organizationId;
        this.sender = sender;
        this.receiver = receiver;
        this.couponType = couponType;
        this.title = title;
        this.message = message;
        this.status = status;
        this.createdDate = createdDate;
    }

    public boolean isOwner(final Long memberId) {
        return sender.isSameId(memberId) || receiver.isSameId(memberId);
    }

    public boolean isInOrganization(final Long organizationId) {
        return this.organizationId.equals(organizationId);
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
        return Objects.equals(couponId, that.couponId) && Objects.equals(organizationId, that.organizationId)
                && Objects.equals(sender, that.sender)
                && Objects.equals(receiver, that.receiver) && Objects.equals(couponType,
                that.couponType) && Objects.equals(title, that.title) && Objects.equals(message,
                that.message) && Objects.equals(status, that.status) && Objects.equals(createdDate,
                that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, organizationId, sender, receiver, couponType, title, message, status,
                createdDate);
    }

    @Override
    public String toString() {
        return "MemberCoupon{" +
                "couponId=" + couponId +
                ", organizationId=" + organizationId +
                ", sender=" + sender.getId() +
                ", receiver=" + receiver.getId() +
                ", couponType='" + couponType + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
