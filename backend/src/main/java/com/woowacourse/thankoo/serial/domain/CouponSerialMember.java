package com.woowacourse.thankoo.serial.domain;

public class CouponSerialMember {

    private final Long id;
    private final String code;
    private final Long memberId;
    private final String memberName;
    private final String couponType;

    public CouponSerialMember(final Long id,
                              final String code,
                              final Long memberId,
                              final String memberName,
                              final String couponType) {
        this.id = id;
        this.code = code;
        this.memberId = memberId;
        this.memberName = memberName;
        this.couponType = couponType;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getCouponType() {
        return couponType;
    }
}
