package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.coupon.domain.CouponType;
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
@Table(name = "serial")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Serial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "coupon_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponType couponType;

    public Serial(final Long id, final String code, final Long memberId, final CouponType couponType) {
        this.id = id;
        this.code = code;
        this.memberId = memberId;
        this.couponType = couponType;
    }

    public Serial(final String code, final Long memberId, final CouponType couponType) {
        this(null, code, memberId, couponType);
    }
}
