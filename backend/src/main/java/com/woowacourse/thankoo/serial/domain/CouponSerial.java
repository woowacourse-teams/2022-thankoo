package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
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
@Table(name = "coupon_serial")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponSerial extends BaseEntity {

    private static final int CODE_LENGTH = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "coupon_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponSerialType couponSerialType;

    public CouponSerial(final Long id, final String code, final Long senderId,
                        final CouponSerialType couponSerialType) {
        validateCode(code);
        this.id = id;
        this.code = code;
        this.senderId = senderId;
        this.couponSerialType = couponSerialType;
    }

    private void validateCode(final String code) {
        if (!isValidCode(code)) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL);
        }
    }

    private static boolean isValidCode(final String code) {
        return !code.isBlank() && code.length() == CODE_LENGTH;
    }

    public CouponSerial(final String code, final Long senderId, final CouponSerialType couponSerialType) {
        this(null, code, senderId, couponSerialType);
    }
}
