package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Embedded
    private CouponContent couponContent;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private CouponStatus couponStatus;

    public Coupon(final Long id,
                  final Long senderId,
                  final Long receiverId,
                  final CouponContent couponContent,
                  final CouponStatus couponStatus) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.couponContent = couponContent;
        this.couponStatus = couponStatus;
    }

    public Coupon(final Long senderId,
                  final Long receiverId,
                  final CouponContent couponContent,
                  final CouponStatus couponStatus) {
        this(null, senderId, receiverId, couponContent, couponStatus);
    }

    public boolean isNotUsed() {
        return couponStatus.isNotUsed();
    }

    public boolean isSameReceiver(final Long memberId) {
        return receiverId.equals(memberId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coupon)) {
            return false;
        }
        Coupon that = (Coupon) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", couponContent=" + couponContent +
                ", couponStatus=" + couponStatus +
                '}';
    }
}
