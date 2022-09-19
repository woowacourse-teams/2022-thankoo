package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.serial.domain.CouponSerialCreator;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.infrastructer.CouponSerialContentFactory;
import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import com.woowacourse.thankoo.serial.domain.CouponSerialQueryRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponSerialService implements CouponSerialCreator {

    private final CouponSerialQueryRepository couponSerialQueryRepository;
    private final MemberRepository memberRepository;

    @Override
    public Coupon create(final Long receiverId, final String serialCode) {
        Member receiver = getMemberById(receiverId);
        CouponSerialMember couponSerialMember = getCouponSerialMember(serialCode);
        CouponSerialContentFactory couponSerialContentFactory = new CouponSerialContentFactory(couponSerialMember);
        return couponSerialMember.createCoupon(receiver.getId(), couponSerialContentFactory.create());
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private CouponSerialMember getCouponSerialMember(final String serialCode) {
        return couponSerialQueryRepository.findByCode(serialCode)
                .orElseThrow(() -> new InvalidCouponSerialException(ErrorType.NOT_FOUND_COUPON_SERIAL));
    }
}
