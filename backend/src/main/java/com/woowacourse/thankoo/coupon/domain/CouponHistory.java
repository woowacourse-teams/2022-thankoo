package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import javax.persistence.Column;
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "coupon_type", nullable = false, length = 20)
    private CouponType couponType;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    public CouponHistory(final Long id,
                         final Long senderId,
                         final Long receiverId,
                         final CouponType couponType,
                         final String title,
                         final String message) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.couponType = couponType;
        this.title = title;
        this.message = message;
    }

    public CouponHistory(final Long senderId,
                         final Long receiverId,
                         final String couponType,
                         final String title,
                         final String message) {
        this(null, senderId, receiverId, CouponType.of(couponType), title, message);
    }
}
