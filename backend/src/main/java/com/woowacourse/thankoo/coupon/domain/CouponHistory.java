package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Embedded
    private CouponContent couponContent;

    public CouponHistory(final Long id,
                         final Long senderId,
                         final Long receiverId,
                         final CouponContent couponContent) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.couponContent = couponContent;
    }

    public CouponHistory(final Long senderId,
                         final Long receiverId,
                         final CouponContent couponContent) {
        this(null, senderId, receiverId, couponContent);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponHistory)) {
            return false;
        }
        CouponHistory that = (CouponHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CouponHistory{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", couponContent=" + couponContent +
                '}';
    }
}
