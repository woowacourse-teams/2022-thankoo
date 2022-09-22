package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.MemberQueryRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import com.woowacourse.thankoo.serial.domain.CouponSerialQueryRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import com.woowacourse.thankoo.serial.presentation.dto.CouponSerialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponSerialQueryService {

    private final CouponSerialQueryRepository couponSerialQueryRepository;
    private final MemberQueryRepository memberQueryRepository;

    public CouponSerialResponse getCouponSerialByCode(final Long memberId, final String code) {
        validateExistedMember(memberId);
        return CouponSerialResponse.from(getCouponSerialMember(code));
    }

    private void validateExistedMember(final Long memberId) {
        if (!memberQueryRepository.existsById(memberId)) {
            throw new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER);
        }
    }

    private CouponSerialMember getCouponSerialMember(final String code) {
        return couponSerialQueryRepository.findByCode(code)
                .orElseThrow(() -> new InvalidCouponSerialException(ErrorType.NOT_FOUND_COUPON_SERIAL));
    }
}
