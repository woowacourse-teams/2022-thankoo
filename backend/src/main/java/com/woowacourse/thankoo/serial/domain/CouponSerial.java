package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
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
@Table(name = "coupon_serial")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponSerial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Embedded
    private SerialCode serialCode;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "coupon_type", nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private CouponSerialType couponSerialType;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private CouponSerialStatus status;

    @Embedded
    private CouponSerialContent content;

    public CouponSerial(final Long id,
                        final Long organizationId,
                        final SerialCode serialCode,
                        final Long senderId,
                        final CouponSerialType couponSerialType,
                        final CouponSerialStatus status,
                        final CouponSerialContent content) {
        this.id = id;
        this.organizationId = organizationId;
        this.serialCode = serialCode;
        this.senderId = senderId;
        this.couponSerialType = couponSerialType;
        this.status = status;
        this.content = content;
    }

    public CouponSerial(final Long organizationId,
                        final SerialCode serialCode,
                        final Long senderId,
                        final CouponSerialType couponSerialType,
                        final CouponSerialStatus status,
                        final CouponSerialContent content) {
        this(null, organizationId, serialCode, senderId, couponSerialType, status, content);
    }

    public CouponSerial(final Long organizationId,
                        final String code,
                        final Long senderId,
                        final CouponSerialType couponSerialType,
                        final CouponSerialStatus status,
                        final String title,
                        final String message) {
        this(null, organizationId, new SerialCode(code), senderId, couponSerialType, status,
                new CouponSerialContent(title, message));
    }

    public void use() {
        if (status.isUsed()) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL_EXPIRATION);
        }
        this.status = CouponSerialStatus.USED;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponSerial)) {
            return false;
        }
        CouponSerial that = (CouponSerial) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CouponSerial{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", serialCode=" + serialCode +
                ", senderId=" + senderId +
                ", couponSerialType=" + couponSerialType +
                ", status=" + status +
                ", content=" + content +
                '}';
    }
}
