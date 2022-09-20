package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import com.woowacourse.thankoo.serial.domain.CouponSerialQueryRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import com.woowacourse.thankoo.serial.infrastructure.CouponSerialContentFactory;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class CouponSerialService {

    private final CouponSerialQueryRepository couponSerialQueryRepository;
    private final CouponSerialRepository couponSerialRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public void use(final Long memberId, final CouponSerialRequest couponSerialRequest) {
        Member receiver = getMemberById(memberId);
        CouponSerialMember couponSerialMember = getCouponSerialMember(couponSerialRequest.getSerialCode());
        validateStatus(couponSerialMember);

        couponSerialRepository.save(couponSerialMember.createUsedCouponSerial());
        couponRepository.save(createCoupon(receiver, couponSerialMember));
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private CouponSerialMember getCouponSerialMember(final String serialCode) {
        return couponSerialQueryRepository.findByCode(serialCode)
                .orElseThrow(() -> new InvalidCouponSerialException(ErrorType.NOT_FOUND_COUPON_SERIAL));
    }

    private static void validateStatus(final CouponSerialMember couponSerialMember) {
        if (couponSerialMember.isUsed()) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL_EXPIRATION);
        }
    }

    private Coupon createCoupon(final Member receiver, final CouponSerialMember couponSerialMember) {
        CouponSerialContentFactory couponSerialContentFactory = new CouponSerialContentFactory(couponSerialMember);
        return couponSerialMember.createCoupon(receiver.getId(), couponSerialContentFactory.create());
    }
}
