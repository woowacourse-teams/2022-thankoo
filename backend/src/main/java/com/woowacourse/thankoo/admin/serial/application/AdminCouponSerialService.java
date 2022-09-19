package com.woowacourse.thankoo.admin.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCouponSerialService {

    private final CouponSerialRepository couponSerialRepository;
    private final MemberRepository memberRepository;

    public Long save(final CouponSerialRequest couponSerialRequest) {
        Member coach = getMember(couponSerialRequest.getMemberId());
        CouponSerial couponSerial = couponSerialRequest.toEntity(coach.getId());
        validateCode(couponSerial.getCode());
        return couponSerialRepository.save(couponSerial).getId();
    }

    private void validateCode(final String code) {
        if (couponSerialRepository.existsByCode(code)) {
            throw new InvalidCouponSerialException(ErrorType.DUPLICATE_COUPON_SERIAL);
        }
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
