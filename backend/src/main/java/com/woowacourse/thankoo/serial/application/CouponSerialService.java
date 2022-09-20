package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialStatus;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class CouponSerialService {

    private final CouponSerialRepository couponSerialRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public void use(final Long memberId, final CouponSerialRequest couponSerialRequest) {
        Member receiver = getMemberById(memberId);
        CouponSerial couponSerial = getSerialByName(couponSerialRequest.getSerialCode());
        Coupon coupon = createCoupon(receiver, couponSerial);
        validateStatus(couponSerial.getStatus());
        couponSerial.use();
        couponRepository.save(coupon);
    }

    private CouponSerial getSerialByName(final String serialCode) {
        return couponSerialRepository.findBySerialCodeValue(serialCode)
                .orElseThrow(() -> new InvalidCouponSerialException(ErrorType.NOT_FOUND_COUPON_SERIAL));
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private void validateStatus(final CouponSerialStatus status) {
        if (status.isUsed()) {
            throw new InvalidCouponSerialException(ErrorType.INVALID_COUPON_SERIAL_EXPIRATION);
        }
    }

    private Coupon createCoupon(final Member receiver, final CouponSerial couponSerial) {
        return new Coupon(couponSerial.getSenderId(), receiver.getId(),
                new CouponContent(CouponType.COFFEE, "Sda", "asd"),
                CouponStatus.NOT_USED);
    }
}
