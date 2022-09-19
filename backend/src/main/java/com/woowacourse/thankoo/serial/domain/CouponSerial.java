package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.util.RandomStringCreator;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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

    public CouponSerial(final Long id,
                        final String code,
                        final Long senderId,
                        final CouponSerialType couponSerialType) {
        this.id = id;
        this.serialCode = new SerialCode(code);
        this.senderId = senderId;
        this.couponSerialType = couponSerialType;
    }

    public CouponSerial(final String code, final Long senderId, final CouponSerialType couponSerialType) {
        this(null, code, senderId, couponSerialType);
    }

//    public CouponSerial(final RandomStringCreator randomStringCreator,
//                        final Long senderId,
//                        final CouponSerialType couponSerialType) {
//        this(null, randomStringCreator.create(CODE_LENGTH), senderId, couponSerialType);
//    }
}
