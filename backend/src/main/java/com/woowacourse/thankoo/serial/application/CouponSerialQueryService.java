package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
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

    public CouponSerialResponse getByCode(final String code) {
        CouponSerialMember couponSerialMember = couponSerialQueryRepository.findByCode(code)
                .orElseThrow(() -> new InvalidCouponSerialException(ErrorType.NOT_FOUND_COUPON_SERIAL));
        return CouponSerialResponse.from(couponSerialMember);
    }
}
