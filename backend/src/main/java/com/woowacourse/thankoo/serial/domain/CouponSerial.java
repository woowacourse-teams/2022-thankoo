package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
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
@Table(name = "coupon_serial")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponSerial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SerialCode serialCode;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "coupon_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponSerialType couponSerialType;

    public CouponSerial(final Long id, final SerialCode serialCode, final Long senderId,
                        final CouponSerialType couponSerialType) {
        this.id = id;
        this.serialCode = serialCode;
        this.senderId = senderId;
        this.couponSerialType = couponSerialType;
    }

    public CouponSerial(final String code, final Long senderId, final CouponSerialType couponSerialType) {
        this(null, new SerialCode(code), senderId, couponSerialType);
    }

    public CouponSerial(final SerialCode serialCode,
                        final Long senderId,
                        final CouponSerialType couponSerialType) {
        this(null, serialCode, senderId, couponSerialType);
    }
}
